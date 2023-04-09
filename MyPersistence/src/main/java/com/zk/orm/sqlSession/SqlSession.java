package com.zk.orm.sqlSession;

import java.util.List;

public interface SqlSession {
    //查询所有
    public <E> List<E> selectList(String statementId, Object... params) throws Exception;

    //根据条件进行单个查询
    public <T> T selectOne(String statementId, Object... params ) throws Exception;

    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
