package fun.xb.easyorm.config;

import fun.xb.easyorm.service.EasySession;
import fun.xb.easyorm.service.EasyormFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(EasyormFactory.class) //存在这个类才会实 例化本配置类
@EnableConfigurationProperties(Properties.class) // 激活@ConfigurationProperties
public class easyormConfiguration {

    private Properties properties;

    @Autowired
    public easyormConfiguration(Properties properties) {
        this.properties = properties;
    }



    @Bean
    @ConditionalOnMissingBean(EasySession.class)
    public EasySession easyorm() {
        EasyormFactory easyormFactory = new EasyormFactory(properties);

        return easyormFactory.createSession();
    }


}
