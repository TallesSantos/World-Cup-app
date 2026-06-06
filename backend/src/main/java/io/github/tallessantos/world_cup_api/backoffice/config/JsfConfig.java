package io.github.tallessantos.world_cup_api.backoffice.config;

import jakarta.faces.webapp.FacesServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsfConfig {

    @Bean
    ServletRegistrationBean<FacesServlet> facesServletRegistration() {
        return new ServletRegistrationBean(
                new FacesServlet(),
                "*.xhtml"
        );
    }
}
