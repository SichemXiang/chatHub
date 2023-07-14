package com.chatHub.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig  implements WebMvcConfigurer{

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
                //允许访问的路径
                .addMapping("/**")
                .allowedOrigins("http://localhost:8888")
                .allowedMethods("GET","POST","DELETE","PUT")
                .allowCredentials(true)
                .maxAge(3600);

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        //swagger相关
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register");
    }
}
