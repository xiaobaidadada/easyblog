package fun.xb.web.controller.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class web_cofig  implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        /**
         * 让拦截器生效
         */
        registry.addInterceptor(new authority()).excludePathPatterns(
                "/file_public/**",
                "/index/**",
                "/blog/**",
                "/comment_for/**",
                "/admin/**",//后台登录
                "/css/**",
                "/js/**",
                "/favicon.ico",//图标？
                "/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                //.allowedMethods("*") //或者放行全部
                //放行哪些原始请求头部信息
                .allowedHeaders("*")
                //暴露哪些原始请求头部信息
                .exposedHeaders("*");
    }

}
