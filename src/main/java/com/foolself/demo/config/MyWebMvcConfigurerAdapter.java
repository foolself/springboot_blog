package com.foolself.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author http://foolself.github.io
 * @date 2018/10/26 20:52
 */
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //指向外部目录
        registry.addResourceHandler("img//**").addResourceLocations("file:E:/img/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
