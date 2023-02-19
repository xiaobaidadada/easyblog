package fun.xb.easyorm.service.datasource;

import fun.xb.easyorm.config.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * 获取bdcp的数据源
 */
public class DBCP {
    private static BasicDataSource basicDataSource;

    public synchronized static DataSource getDataSource(Properties properties){
        basicDataSource = new BasicDataSource();

        basicDataSource.setUrl(properties.getUrl());
        basicDataSource.setDriverClassName(properties.getDriverClassName());
        basicDataSource.setUsername(properties.getUsername());
        basicDataSource.setPassword(properties.getPassword());


        /**
         * 默认数量连接
         */
        basicDataSource.setInitialSize(30);
        basicDataSource.setMaxIdle(1500);
        basicDataSource.setMinIdle(15);
        return basicDataSource;
    }
}
