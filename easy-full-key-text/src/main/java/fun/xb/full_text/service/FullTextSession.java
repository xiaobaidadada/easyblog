package fun.xb.full_text.service;

import java.util.List;

/**
 * 支持全部基本数据类型，没有加注解的不会被索引，不支持集合形式的数据
 * 目前只是高级抽象工具类，不是一个服务器级别的处理，不支持抽象数据类似，和插入更新删除语句；
 * @param <T>
 */
public interface FullTextSession<T> {


    /**
     * 只保存被标记的字段，并进行中文分词索引
     * @param dto
     */
    public void insert(T dto);

    /**
     * 更新和删除，依然遵循，如果id存在就按id查，不存在就按别的所有字段进行分词查
     * @param dto
     */
    public long update(T dto,T query_dto);

    public long delete(T query_dto);

    /**
     *  该查询会查询全部的数据 不做分页；小型项目足够用；
     *  由于目的是文本全文所以，所以目前只能查字符串，如果id不是空，那么别的查询字段都失效；否则别的查询字段只要不为空，就可以被查到，只能做分词全文索引查询
     * @param dto
     * @return
     */
    public List<T> query(T dto);

}
