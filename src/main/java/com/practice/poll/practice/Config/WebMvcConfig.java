package com.practice.poll.practice.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { //WE ENABLING CORS

    private final long MAX_AGE_SECS = 3600; //Run on all web

    @Value("${app.cors.allowedOrigins}")
    private String [] allowedOrigins;

    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("HEAD","OPTIONS","GET","POST","PATCH","DELETE")
                .maxAge(MAX_AGE_SECS);
    }

//CONFIGURE THE REMAINS IN APP.PROPERTIES
}
