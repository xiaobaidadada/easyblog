package fun.xb.full_text.config;


import fun.xb.full_text.service.FullTextSession;
import fun.xb.full_text.service.datasource.EasyFullTextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(EasyFullTextFactory.class) //存在这个类才会实 例化本配置类
@EnableConfigurationProperties(Properties.class) // 激活@ConfigurationProperties
public class EasyFullTextConfiguration {

    private Properties properties;

    @Autowired
    public EasyFullTextConfiguration(Properties properties) {
        this.properties = properties;
    }


    @Bean
//    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)//原型创建 不共用一个session，在这里公用一个 由dbutils来完成对连接池的调用
    @ConditionalOnMissingBean(EasyFullTextFactory.class)
    public FullTextSession easyorm() {
        EasyFullTextFactory factory = new EasyFullTextFactory(properties.getHomePath());

        return factory.createSession();
    }


}
