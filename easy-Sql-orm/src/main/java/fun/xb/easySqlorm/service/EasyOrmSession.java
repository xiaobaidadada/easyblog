package fun.xb.easySqlorm.service;

import fun.xb.easySqlorm.util.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 会话工厂，默认开启驼峰转下划线；
 */
public class EasyOrmSession implements SqlSession {
    private QueryRunner qr;

    public EasyOrmSession(QueryRunner qr){
        this.qr=qr;
    }
    /**
     * 插入任意一种类型的dto到数据库，会返回主键值
     * @param dto
     * @return
     * @param <T>
     */
    public <T>  T insert(T dto)  {
        String tableName=null;//表名
        tableName= DTOUtil.getClassTableName(dto.getClass());

        List<String > columns=new ArrayList<>();//列名
        List columnValueS=new ArrayList<>();//列名的值
        try {
            columns=DTOUtil.getAllColumns(dto,columnValueS);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
/**
 * 设置属性
 */

        String into="(";

        for (int i=0;i<columns.size()-1;i++){

            into+=columns.get(i)+",";

        }

        into+=columns.get(columns.size()-1)+")";//最后一个元素
/**
 * 设置属性值
 */
        String values="(";
        for (int i=0;i<columnValueS.size()-1;i++){
            if (columnValueS.get(i) instanceof String){//占位符可以避免这种自己判断是不是字符串的情况
                values+="?,";
            }
            else {
                values+=" ? ,";
            }
        }
        if(columnValueS.get(columnValueS.size()-1) instanceof String){
            values+=" ? )";
        }else {
            values+=" ? )";
        }

        String sql="insert into "+tableName+into+" values "+values;
        T t= null;
        try {
            t = (T) qr.insert(sql, new ResultSetHandler() {
                @Override
                public T handle(ResultSet rs) throws SQLException {
                    if(rs.next()){
                        try {
                            T t= (T) dto.getClass().getDeclaredConstructor().newInstance();
                            DTOUtil.setId(dto,rs.getObject(1));
                            return  t;
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    return null;
                }
            },columnValueS.stream().toArray());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        POJOUtil.copyProperties(dto,t);
        return t;
    }

    /**
     * 删除函数
     * @param dto
     * @return
     * @param <T>
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> int deleteById(T dto) {
        String tableName=null;//表名
        tableName= DTOUtil.getClassTableName(dto.getClass());

        Object id=null;
        try {
            id=DTOUtil.getId(dto);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        String sql=null;
        if(id instanceof String ){
             sql="DELETE FROM "+tableName+"  WHERE id = ?";
        }else {
            sql = "DELETE FROM " + tableName + " WHERE id = ?";
        }
        int i= 0;
        try {
            i = qr.update(sql,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    /**
     * 此sql不要带where
     * @param dto
     * @param whereSql
     * @return
     * @param <T>
     */
    public <T> int deleteByWhereSql(T dto,String whereSql,Object ... parms) {
        String tableName=null;//表名
        tableName= DTOUtil.getClassTableName(dto.getClass());

        String allSql=null;
        allSql = "DELETE FROM " + tableName + " WHERE "+whereSql;

        int i= 0;

        try {
            i = qr.update(allSql,parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    /**
     * 更新函数
     * @param dto
     * @return
     * @param <T>
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public <T> int updateById(T dto)  {
        String tableName=null;//表名
        tableName= DTOUtil.getClassTableName(dto.getClass());

        Object id=null;
        try {
            id=DTOUtil.getId(dto);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        List<String > columns=new ArrayList<>();//列名
        List columnValueS=new ArrayList<>();//列名的值
        try {
            columns=DTOUtil.getAllColumns(dto,columnValueS);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        String update=" ";
        for(int i=0;i<columns.size()-1;i++){
                update+=columns.get(i)+" = ? ,";
        }
        update+=columns.get(columns.size()-1)+"= ? ";

        columnValueS.add(id);//最后放id

        String   sql="UPDATE "+tableName+" SET "+update+" where id = ? ";

        int i= 0;
        try {
            i = qr.update(sql,columnValueS.stream().toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;

    }
    public <T> int updateByWhereSql(T dto,String whereSql,Object ... parms)  {
        String tableName=null;//表名
        tableName= DTOUtil.getClassTableName(dto.getClass());

        List<String > columns=new ArrayList<>();//列名
        List columnValueS=new ArrayList<>();//列名的值
        try {
            columns=DTOUtil.getAllColumns(dto,columnValueS);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        String update=" ";
        for(int i=0;i<columns.size()-1;i++){
            update+=columns.get(i)+" = ? ,";
        }
        update+=columns.get(columns.size()-1)+"= ? ";


        String   sql="UPDATE "+tableName+" SET "+update+" where  "+whereSql;

        int i= 0;
        Object [] colums=columnValueS.stream().toArray();
        Object [] totalArr=new Object[parms.length+colums.length];
        System.arraycopy(colums,0,totalArr,0,colums.length);
        System.arraycopy(parms,0,totalArr,colums.length,parms.length);

        try {
            i = qr.update(sql,totalArr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;

    }



    public  List<Object> select(String sql,Object ... parms)  {
        List<Object> list= null;
        try {
            list = qr.query(sql, new ColumnListHandler<Object>(),parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Map<String,Object> selectOne(String sql,Object ... parms){
        Map<String,Object> map= null;
        try {
            map = qr.query(sql,new MapHandler(), parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public <T> T selectOne(String sql,Class<T> c,Object ... parms)  {
        T o= null;
        try {
            o = qr.query(sql,new BeanHandler<T>(c),parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    /**
     * 实现按sql查询的关联语句
     * @param sql
     * @param c
     * @return
     * @param <T>
     * @throws SQLException
     */

    public <T> List<T>  select(String sql,Class<T> c,Object ... parms)  {
        /**
         * 注意：如果关联查询多个属性相同，要用标识符标识，否则会覆盖同名属性；
         */

        List<Map<String, Object>> maps;
        try {
            maps = qr.query(sql, new MapListHandler(),parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return handle(maps,c);

    }

    /**
     * 带有分页功能的查询
     * @param sql
     * @param c
     * @return
     * @param <T>
     */
    public <T> easyormPage<T> selectPage(String sql, Class<T> c, easyormPage<T> page, Object ... parms)  {
        /**
         * 注意：如果关联查询多个属性相同，要用标识符标识，否则会覆盖同名属性；
         */
        Long total=selectCount(sql,parms);

        if(page.getType()== PageType.POSTGRESQL){
            sql+=" LIMIT "+page.getSize()+" OFFSET " +(page.getPage()-1);
        }
        if(page.getType()==PageType.MYSQL){
            sql+=" LIMIT "+(page.getPage() -1)+" , " +page.getSize();
        }

        List<Map<String, Object>> maps;
        try {
            maps = qr.query(sql, new MapListHandler(),parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<T> res=handle(maps,c);

        page.setList(res);
        page.setTotal(Math.toIntExact(total));
        page.setPages(easyormPage.getPages(page.getTotal(),page.getSize()));
        return page;
    }


    /**
     * 目前不支持分组查询个数
     * @param sql
     * @param parms
     * @return
     */
    public Long selectCount(String sql,Object ... parms)  {
        Long count= null;
        sql="select count(*) "+sql.substring(sql.indexOf("from"));
        try {
            count = (Long) qr.query(sql, new ResultSetHandler() {
                @Override
                public Long handle(ResultSet rs) throws SQLException {
                    return rs.getLong(1);

                }
            },parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    /**
     * 处理查询后的句子，支持普通，一对一，一对多 三种结果映射
     * @param maps
     * @param c
     * @return
     * @param <T>
     */
    private <T> List<T> handle(List<Map<String, Object>> maps,Class<T> c){
        List<T> reList=new ArrayList<>();
        Map map= null;
        try {
            map = DTOUtil.getMapTYpeAndPropertities(c);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        int type= (int) map.get("type");

        if(type== MapType.ONE){
            List<Map> ones= (List<Map>) map.get("ones");//他们的列 和 对象值
            Map<String,String> mapp=(Map<String, String>) map.get("maps");

            for (Map<String,Object> rm:maps){//每一行
                T t= null;
                try {
                    t = c.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                List<Map> oness=new ArrayList<>();
                for (Map one:ones)
                {
                    Object  mapmap = null;
                    try {
                        mapmap = one.get("value").getClass().getDeclaredConstructor().newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    Map v=new HashMap<>();
                    v.put("properyity",one.get("properyity"));
                    v.put("value",mapmap);
                    oness.add(v);
                }
                T finalT = t;
                rm.forEach((s, o)->{//以列进行遍历
                    if(!((HashMap)mapp).containsKey(s)){
                        for(Map o1:oness){
                            Map  mapmap  = null;
                            try {
                                mapmap = DTOUtil.getMapTYpeAndPropertities(o1.get("value").getClass());
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            Map<String,String> mappp=(Map<String, String>) mapmap.get("maps");
                            String pp= (String) mappp.get(s);
                            if(pp!=null){
                                DTOUtil.setPartOfProperty(o1.get("value"),pp,o);//设置对应对象的属性，如果属性存在这个对象中
                            }
                        }

                    }else {
                        String propertityName= mapp.get(s);
                        DTOUtil.setPartOfProperty(finalT,propertityName,o);

                    }
                });
                T finalT1 = t;
                oness.forEach((s1)->{
                    Object s4=((Map)s1).get("value");
                    String s3= (String) ((Map)s1).get("properyity");
                    DTOUtil.setPartOfProperty((Object) finalT1, (String) s3,s4);
                });
                reList.add(t);
            }
        }
        else if(type==MapType.MANY){
            List<Map> manys= (List<Map>) map.get("manys");//多个list包含的类
            Map<String,String> mapp=(Map<String, String>) map.get("maps");//普通属性字段

            for (Map<String,Object> rm:maps){//每一行
                T t= null;
                try {
                    t = c.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }

                AtomicBoolean have= new AtomicBoolean(true);
                reList.forEach(v->{//已经存在的每一行
                    AtomicBoolean is= new AtomicBoolean(true);
                    mapp.forEach((s1,s2)->{
                        try {
                            Field field=v.getClass().getDeclaredField(s2);
                            field.setAccessible(true);
                            if(!rm.get(s1).equals(field.get(v))){
                                is.set(false);
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchFieldException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    if(is.get()){//已经存在某行
                        manys.forEach(ma->{
                            Class t1= (Class) ma.get("value");
                            Object o1= null;
                            try {
                                o1 = t1.getDeclaredConstructor().newInstance();
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            Map map2= null;
                            try {
                                map2 = DTOUtil.getMapTYpeAndPropertities(o1.getClass());
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            Map<String,String> mapp1=(Map<String, String>) map2.get("maps");
                            Object finalO = o1;
                            rm.forEach((s1, v1)->{
                                if(((HashMap)mapp1).containsKey(s1)){
                                    DTOUtil.setPartOfProperty(finalO,mapp1.get(s1),v1);
                                }
                            });
                            try {
                                Field field= v.getClass().getDeclaredField((String) ma.get("properyity"));
                                field.setAccessible(true);
                                ((List) field.get(v)).add(o1);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (NoSuchFieldException e) {
                                throw new RuntimeException(e);
                            }
                            have.set(false);
                        });
                    }

                });
                if(have.get())//如果没有发生行存在的情况
                {//不存在某行
                    List<Map> manys1= new ArrayList<>();
                    manys.forEach(v->{
                        Map map1=new HashMap<>();
                        map1.put("properyity",v.get("properyity"));
                        map1.put("value",new ArrayList<>());
                        manys1.add(map1);
                    });
                    List<Map> manys2= new ArrayList<>();
                    manys.forEach(v->{
                        Object o= null;
                        try {
                            o = ((Class)v.get("value")).getDeclaredConstructor().newInstance();
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                        Map map1=new HashMap<>();
                        map1.put("properyity",v.get("properyity"));
                        map1.put("value",o);
                        manys2.add(map1);
                    });

                    T finalT1 = t;
                    rm.forEach((s, o)->{//以列进行遍历

                        if(!((HashMap)mapp).containsKey(s)){
                            manys.forEach(v->{
                                Map map1= null;
                                try {
                                    map1 = DTOUtil.getMapTYpeAndPropertities((Class) v.get("value"));
                                } catch (NoSuchMethodException e) {
                                    throw new RuntimeException(e);
                                } catch (InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                } catch (InstantiationException e) {
                                    throw new RuntimeException(e);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                Map<String,String> mapp1=(Map<String, String>) map1.get("maps");//普通属性字段
                                Map finalMap = map1;
                                mapp1.forEach((s1, s2)->{
                                    if(s.equals(s1)){
                                        manys2.forEach(v1->{
                                            if(v.get("properyity").equals(v1.get("properyity"))){
                                                DTOUtil.setPartOfProperty(v1.get("value"),s2,o);
                                            }
                                        });
                                    }
                                });
                            });

                        }else {
                            String propertityName= mapp.get(s);
                            DTOUtil.setPartOfProperty(finalT1,propertityName,o);

                        }
                    });
                    T finalT = t;
                    manys1.forEach(v->{
                        manys2.forEach(v1->{
                            if(v.get("properyity").equals(v1.get("properyity"))){
                                ((List)v.get("value")).add(v1.get("value"));
                            }
                        });
                        DTOUtil.setPartOfProperty((Object) finalT, (String) v.get("properyity"),v.get("value"));
                    });
                    reList.add(t);
                }

            }

        }
        else if(type==MapType.PUB){
            Map<String,String> mapp=(Map<String, String>) map.get("maps");
            for (Map<String,Object> rm:maps){//每一行
                T t= null;
                try {
                    t = c.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                T finalT = t;
                rm.forEach((s, o)->{//以列进行遍历
                    String propertityName= mapp.get(s);
                    DTOUtil.setPartOfProperty(finalT,propertityName,o);
                });
                reList.add(t);
            }
        }


        return reList;
    }

}
