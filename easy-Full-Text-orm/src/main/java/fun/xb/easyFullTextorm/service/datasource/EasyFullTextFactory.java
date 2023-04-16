package fun.xb.easyFullTextorm.service.datasource;


import fun.xb.easyFullTextorm.service.EasySession;
import fun.xb.easyFullTextorm.service.FullTextSession;

/**
 * 会话工厂
 */
public class EasyFullTextFactory {

    sourConfig config;
    public EasyFullTextFactory(String home_path){
        config = new sourConfig();
        config.home_path = home_path;
    }

    //构造函数可以是一些 配置

    //创建会话工厂
    public FullTextSession createSession()  {

        EasyFullTextSource easyFullTextSource = new EasyFullTextSource(config);
        return new EasySession(easyFullTextSource);
    }

}
