package com.zk.orm.sqlSession;

import com.zk.orm.pojo.Configuration;
import com.zk.orm.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSession implements SqlSession{

    private Configuration configuration;

    public DefaultSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        // 完成对simpleExecutor里的query方法的调用
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> queryList = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E> )queryList;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if(objects.size() == 1){
            return (T) objects.get(0);
        }
        return null;
    }

    //为Dao接口生成代理实现类
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //底层还是使用jdbc代码，但是根据情况调用不同底层实现方法
                //准备参数1, statementId（sql语句的唯一标识），由接口全限定类名.方法名组成
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + '.' + methodName;

                //准备参数2： params-args
                //获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                //判断被调用方法的返回值类型
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }
                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }
}
