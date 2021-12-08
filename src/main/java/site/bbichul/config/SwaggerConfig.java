package site.bbichul.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("bbichul.site")
                .pathsToMatch("/api/**")
                .packagesToScan("site.bbichul.controller")
                .build();
    }
}
