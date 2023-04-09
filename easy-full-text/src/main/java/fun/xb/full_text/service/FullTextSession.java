package fun.xb.full_text.service;

import java.util.List;

public interface FullTextSession<T> {


    /**
     * 只保存被标记的字段，并进行中文分词索引
     * @param dto
     */
    public void insert(T dto);

    public void update(T dto);

    public void delete(T dto);

    /**
     *  该查询会查询全部的数据 不做分页；小型项目足够用；
     * @param dto
     * @return
     */
    public List<T> query(T dto);

}
