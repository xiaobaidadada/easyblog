package fun.xb.config.swagger;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;


import java.util.Optional;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * SpringDoc 标题、描述、版本等信息配置
     *
     * @return openApi 配置信息
     */
    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("easyblog")
                        .description("接口文档")
                        .version("1"));

    }

    /**
     * demo 分组
     *
     * @return demo分组接口
     */
    @Bean
    public GroupedOpenApi siteApi() {
        return GroupedOpenApi.builder()
                .group("全部接口")
                .pathsToMatch("/**")
                .build();
    }

    /**
     * sys 分组
     *
     * @return sys分组接口
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("sys接口")
                .pathsToMatch("/sys/**")
                .build();
    }



}