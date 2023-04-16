package fun.xb.easySqlorm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 获取数据源信息
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class Properties {

    // Starter使用者没在配置文件中配置prefixName属性的值时的默认值
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    private String username;

    private String password;

    // 配置文件中是driver-class-name, 转驼峰命名便可以绑定成
    private String driverClassName;


}
