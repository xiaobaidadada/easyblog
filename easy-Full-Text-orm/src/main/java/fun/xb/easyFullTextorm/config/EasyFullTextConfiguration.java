package fun.xb.easyFullTextorm.config;


import fun.xb.easyFullTextorm.service.FullTextSession;
import fun.xb.easyFullTextorm.service.datasource.EasyFullTextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(EasyFullTextFactory.class) //存在这个类才会实 例化本配置类
@EnableConfigurationProperties(FullTextProperties.class) // 激活@ConfigurationProperties
public class EasyFullTextConfiguration {

    private FullTextProperties properties;

    @Autowired
    public EasyFullTextConfiguration(FullTextProperties properties) {
        this.properties = properties;
    }


    @Bean
//    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)//原型创建 不共用一个session，在这里公用一个 由dbutils来完成对连接池的调用
    @ConditionalOnMissingBean(EasyFullTextFactory.class)
    public FullTextSession easyFullText() {
        EasyFullTextFactory factory = new EasyFullTextFactory(properties.getHomePath());

        return factory.createSession();
    }


}
