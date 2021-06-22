package com.ding.airlinewebservice;

import com.ding.airlinewebservice.aop.LoggingInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
    }

    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI().info(new Info().title("Airline WebService")
                .description("This webservice provides flight information")
                .version("1.0.0")
                .termsOfService("http://ding.com/termsofservice")
                .license(new License().url("http://ding.com/license")));
    }
}
