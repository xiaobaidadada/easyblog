package fun.xb.easySqlorm.service.datasource;


import fun.xb.easySqlorm.config.Properties;
import fun.xb.easySqlorm.service.EasySession;
import org.apache.commons.dbutils.QueryRunner;

public class EasyormFactory {

    private Properties properties;

    private QueryRunner qr;

    public EasyormFactory(Properties Properties){
        this.properties=Properties;
        this.qr= new QueryRunner(DBCP.getDataSource(this.properties));
    }

    public EasySession createSession(){//工厂模式，多个session复用一个qr

        return new EasySession(qr);
    }



}
