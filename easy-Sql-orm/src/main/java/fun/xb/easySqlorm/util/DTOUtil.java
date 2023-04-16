package fun.xb.easySqlorm.util;


import fun.xb.easySqlorm.service.annotation.*;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DTOUtil {

    /**
     * 获取对象对应的表名
     * @param c
     * @return
     */
    public static String getClassTableName(Class c){
        String tableName=null;//表名

        Annotation[] annotations =c.getAnnotations();
        //获取表的名字
        for (Annotation ann:
                annotations) {
            if(ann.annotationType().equals(table.class)){
                tableName=((table)ann).value();
            }
        }
        if(tableName==null){//若没有注解将其名字注入
            tableName= HupmAndline.humpToUnderline(c.getSimpleName());
        }
        return tableName;
    }

    /**
     * 获取对象对应的所有的列名（不为null的属性），加了注解就用注解的，每家就按驼峰来转换(不包括主键和null的列属性）
     * @param dto
     * @param columnValueS 属性会对应设置对应的属性名
     * @return 返回的是列名
     */
    public static List<String > getAllColumns(Object dto,List columnValueS) throws IllegalAccessException {
        List<String > columns=new ArrayList<>();//列名

        List<Field> list = Arrays.asList(dto.getClass().getDeclaredFields());


        //获取每一个属性和值
        for (Field f:
                list) {
            boolean i=false;
            boolean br=false;
            f.setAccessible(true);

            if(f.get(dto)==null){// 属性为null属性跳过
                continue;
            }
            for (Annotation ann:
                    f.getAnnotations()) {
                if (ann.annotationType().equals(id.class) ){//插入跳过id
                    br=true;
                    break;
                } else if ( ann.annotationType().equals(column.class)) {
                    columns.add(((column)ann).value());
                    i=true;
                    break;
                }
            }
            if(br){//跳过本次循环，不需要设置id
                continue;
            }

            if(i==false){//按属性名字获取列
                columns.add(HupmAndline.humpToUnderline(f.getName()));
                columnValueS.add(f.get(dto));
            }else {

                columnValueS.add( f.get(dto));
            }
        }
        return columns;
    }

    /**
     * 获取起dto对应表的Id
     * @param dto
     * @return
     * @throws IllegalAccessException
     */
    public static Object getId(Object dto) throws IllegalAccessException {
        Object id=null;
        List<Field> list = Arrays.asList(dto.getClass().getDeclaredFields());
        for (Field f:list) {
            boolean br=false;

            for (Annotation ann: f.getAnnotations()) {
                if (ann.annotationType().equals(id.class) ){//插入跳过id
                    f.setAccessible(true);
                    id=f.get(dto);
                    br=true;
                    break;
                }
            }
            if(br){
                break;
            }else {
                f.setAccessible(true);
                if(f.getName().equals("id")){

                    id=f.get(dto);

                }
            }

        }
        return id;
    }

    /**
     * 返回dto的关联类型，并得到它的 列名 和对应的 属性名
     * @return
     */

    static public Map getMapTYpeAndPropertities(Class c) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        /*
        返回参数预设
         */
        int type=0;
        List<Map> ones=new ArrayList<>();//如果存在是一对一映射参数
        List<Map> manys=new ArrayList<>();//如果存在是一对多映射参数
        Map<String,String> maps=new HashMap<>();//普通参数

        List<Field> list = Arrays.asList(c.getDeclaredFields());
        for (Field f:list) {
            f.setAccessible(true);

            for (Annotation ann: f.getAnnotations()) {
                if (ann.annotationType().equals(id.class) ){

                    maps.put(((id)ann).value(),f.getName());

                }
                else if(ann.annotationType().equals(column.class)){

                    maps.put(((column)ann).value(),f.getName());

                }
                else if(ann.annotationType().equals(one.class)){
                    Map v=new HashMap<>();
                    Object o=f.getType().getDeclaredConstructor().newInstance();
                    v.put("properyity",f.getName());//整个对象后面会获取它的全部，这里只是为了赋值
                    v.put("value",o);
                    ones.add(v);
                    type=MapType.ONE;
                }
                else if(ann.annotationType().equals(many.class)){
                    Map v=new HashMap<>();
                    String st=f.getGenericType().getTypeName();
                    Matcher matcher = Pattern.compile("<(.*?)>").matcher(st);
                    if(matcher.find()){
                        Class className=Class.forName(matcher.group(1));
                        v.put("properyity",f.getName());
                        v.put("value",className);
                        type=MapType.MANY;
                        manys.add(v);
                    }


                }

            }
            if(f.getAnnotations().length==0){
                maps.put(HupmAndline.humpToUnderline(f.getName()),f.getName());
            }
        }
        Map map=new HashMap<>();
        map.put("type",type);
        if(ones.size()!=0){
            map.put("ones",ones);
        }
        if(manys.size()!=0){
            map.put("manys",manys);
        }
        map.put("maps",maps);
        return map;
    }


    /**
     * 给对象oo 特定的属性设置特定的值value
     */
    public static  void setPartOfProperty(Object oo,String name,Object value){


        Class target=oo.getClass();
        Field[] targetFields = target.getDeclaredFields();//目标属性集合
        for (Field fieldTarget : targetFields) {

                if(fieldTarget.getName().equals(name)){
                    fieldTarget.setAccessible(true);
                    try {
                        if(value!=null)
                        fieldTarget.set(oo,TypeConverter.selfType_to_otherType(fieldTarget.getType().getTypeName(),value));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
            }
        }


    }

    /**
     * 为对象的id字段进行赋值
     * @param dto
     * @return
     * @throws IllegalAccessException
     */
    public static void setId(Object dto,Object value) throws IllegalAccessException {
        Object id=null;
        List<Field> list = Arrays.asList(dto.getClass().getDeclaredFields());
        for (Field f:list) {
            boolean br=false;

            for (Annotation ann: f.getAnnotations()) {
                if (ann.annotationType().equals(id.class) ){//插入跳过id
                    f.setAccessible(true);
                    f.set(dto,value);
                    br=true;
                    break;
                }
            }
            if(br){
                break;
            }else {
                f.setAccessible(true);
                if(f.getName().equals("id")){

                    f.set(dto,value);

                }
            }

        }

    }

}
