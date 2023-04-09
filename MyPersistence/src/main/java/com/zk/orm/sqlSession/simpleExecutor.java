package com.zk.orm.sqlSession;

import com.zk.orm.pojo.Configuration;
import com.zk.orm.pojo.MappedStatement;
import com.zk.orm.util.ParameterMapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class simpleExecutor implements  Executor{
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 获取sql语句如：select * from user where id = ? and username = ？
        String sql = mappedStatement.getSql();

        //获取处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

       // 将？替换成具体的实际参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterClassType = getClassType(paramterType);
        List<ParameterMapping> parameterMappingList = mappedStatement.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            // 反射获取字段
            Field declaredField = paramterClassType.getDeclaredField(content);

            //暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params);

            //设置参数
            preparedStatement.setObject(i+1,o);
        }
      // 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<>();
        //封装结果集
        while(resultSet.next()){
            Object o = resultTypeClass.newInstance();
            //获取返回结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段的值
                Object value = resultSet.getObject(columnName);

                //使用反射， 根据数据库表和实体的对应关系，完成对应字段的封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            objects.add(o);
        }

        return (List<E>) objects;
    }

    private Class<?> getClassType(String paramterType) throws  ClassNotFoundException{
        if(paramterType != null){
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;
    }
}
