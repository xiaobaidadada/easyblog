package com.zk.orm.sqlSession;

import com.zk.orm.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
    public Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSession(configuration);
    }
}
