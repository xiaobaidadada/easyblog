package fun.xb.easySqlorm.config;

import fun.xb.easySqlorm.service.EasyOrmSession;
import fun.xb.easySqlorm.service.datasource.EasyormFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(EasyormFactory.class) //存在这个类才会实 例化本配置类
@EnableConfigurationProperties(Properties.class) // 激活@ConfigurationProperties
public class easyOrmConfiguration {

    private Properties properties;

    @Autowired
    public easyOrmConfiguration(Properties p) {
        this.properties = p;
    }



    @Bean
//    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)//原型创建 不共用一个session，在这里公用一个 由dbutils来完成对连接池的调用
    @ConditionalOnMissingBean(EasyOrmSession.class)
    public EasyOrmSession easyOrm() {
        EasyormFactory factory = new EasyormFactory(properties);

        return factory.createSession();
    }


}
