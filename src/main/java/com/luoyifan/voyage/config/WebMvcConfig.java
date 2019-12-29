package com.luoyifan.voyage.config;

import com.luoyifan.voyage.controller.interceptor.BaseAttributeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author EvanLuo
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private BaseAttributeInterceptor baseAttributeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseAttributeInterceptor).addPathPatterns("/**");
    }
}
