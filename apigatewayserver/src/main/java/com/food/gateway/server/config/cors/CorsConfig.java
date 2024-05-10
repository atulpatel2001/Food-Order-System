package com.food.gateway.server.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins, you may want to restrict this based on your requirements
        config.addAllowedOrigin("*");

        // Allow all HTTP methods
        config.addAllowedMethod("*");

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow credentials
        config.setAllowCredentials(true);

        // Configure URL mapping
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
