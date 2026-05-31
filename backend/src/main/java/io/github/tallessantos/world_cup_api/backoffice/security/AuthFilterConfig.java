package io.github.tallessantos.world_cup_api.backoffice.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthFilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration(
            AuthFilter authFilter) {

        FilterRegistrationBean<AuthFilter> registration =
                new FilterRegistrationBean<>(authFilter);

        registration.addUrlPatterns("/backoffice/*");

        registration.setName("authFilter");
        registration.setOrder(1);

        return registration;
    }
}