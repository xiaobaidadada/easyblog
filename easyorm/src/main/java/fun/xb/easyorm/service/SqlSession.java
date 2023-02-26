package fun.xb.easyorm.service;

import fun.xb.easyorm.util.SelectPage;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SqlSession {
    /**
     * 插入任意一种类型的dto到数据库，会返回主键值
     * @param dto
     * @return
     * @param <T>
     */
    public <T>  T insert(T dto);

    /**
     * 通过删除记录函数
     * @param dto
     * @return
     * @param <T>
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> int deleteById(T dto) ;

    /**
     * 通过where语句来删除，参数可有可无，有的话，sql语句需要留出 ? 占位符
     * @param dto
     * @param whereSql
     * @param parms
     * @return
     * @param <T>
     */
    public <T> int deleteByWhereSql(T dto,String whereSql,Object ... parms);

    /**
     * 通过id更新函数
     * @param dto
     * @return
     * @param <T>
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public <T> int updateById(T dto) ;

    /**
     * 通过where sql语句更新记录
     * @param dto
     * @param whereSql
     * @param parms
     * @return
     * @param <T>
     */
    public <T> int updateByWhereSql(T dto,String whereSql,Object ... parms);

    /**
     * 通过sql语句返回一个，自动生成的对象列表，自动注入属性
     * @param sql
     * @param parms
     * @return
     */
    public List<Object> select(String sql, Object ... parms);

    /**
     * 通过sql语句返回一个，具有属性的 map，唯一记录对象，如果有多个只返回第一个
     * @param sql
     * @param parms
     * @return
     */
    public Map<String,Object> selectOne(String sql, Object ... parms);

    /**
     * 根据sql语句，和参数类型，返回一个具体类型的对象
     * @param sql
     * @param c
     * @param parms
     * @return
     * @param <T>
     */
    public <T> T selectOne(String sql,Class<T> c,Object ... parms);

    /**
     * 返回一个特定对象列表的集合，支持普通，一对一，一对多
     * @param sql
     * @param c
     * @param parms
     * @return
     * @param <T>
     */
    public <T> List<T>  select(String sql,Class<T> c,Object ... parms);

    /**
     * 带有分页功能的查询，特定对象，也支持普通，一对一，一对多
     * @param sql
     * @param c
     * @param page
     * @param parms
     * @return
     * @param <T>
     */
    public <T> SelectPage<T> selectPage(String sql, Class<T> c, SelectPage<T> page, Object ... parms);

    /**
     * 返回记录查询的总数量 请输入正常的sql语句，会自动把select转化成 conut(*)
     * @param sql
     * @param parms
     * @return
     */
    public Long selectCount(String sql,Object ... parms);

}
