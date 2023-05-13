package fun.xb.easyFullTextorm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 获取数据源信息
 */
@ConfigurationProperties(prefix = "easy.fulltext")
public class FullTextProperties {

    // Starter使用者没在配置文件中配置prefixName属性的值时的默认值
    private String homePath;

    public String getHomePath() {
        return homePath;
    }

    public void setHomePath(String homePath) {
        this.homePath = homePath;
    }
}
