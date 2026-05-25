package io.github.tallessantos.world_cup_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {


    @Value("${resource.storage.path}")
    private String storagePath;

    @Value("${resource.server-side-pattern-path}")
    private String pathPatterns;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceLocation = Path.of(storagePath).toUri().toString();
        registry
                .addResourceHandler(pathPatterns)
                .addResourceLocations(resourceLocation);
    }
}
