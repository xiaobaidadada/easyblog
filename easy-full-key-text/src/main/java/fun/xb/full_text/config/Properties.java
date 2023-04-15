package fun.xb.full_text.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 获取数据源信息
 */
@ConfigurationProperties(prefix = "easy.fulltext")
public class Properties {

    // Starter使用者没在配置文件中配置prefixName属性的值时的默认值
    private String home_path;

    public String getHomePath() {
        return home_path;
    }




}
