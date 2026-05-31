package io.github.tallessantos.world_cup_api.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppCommonConfigurationVariables {

    @Getter
    @Value("${app.resource.storage.path}")
    private String storagePath;

    @Getter
    @Value("${app.resource.server-side-pattern-path}")
    private String resourcePath;
}
