package com.example.backend.config;

import com.example.backend.interception.LoginInterception;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterception())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin","/admin/login");
    }
}
