package com.example.bai5;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This tells Spring Boot to serve URLs like /images/... from the physical "uploads" folder
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/");
    }
}