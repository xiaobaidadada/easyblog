package fun.xb.web.controller.interceptor;


import org.springframework.context.annotation.Configuration;
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
                "/admin/**",
                "/");
    }

}
