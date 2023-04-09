package com.zk.orm.sqlSession;

import com.zk.orm.pojo.Configuration;
import com.zk.orm.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    public <E> List<E>  query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
