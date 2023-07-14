package com.chatHub.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/21 21:42
 **/

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    //创建接口文档
    @Bean
    public Docket createApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.snackshop.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    //设置文档信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("chaHub")
                .version("1.0.0")
                .description("chatHub接口文档")
                .build();
    }




}
