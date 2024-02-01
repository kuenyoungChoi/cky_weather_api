package cky.cky_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Weather API",
                description = "날씨 api명세서입니다.",
                version = "v1")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi weatherOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("Sample v1")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi videoOpenApi() {
        String[] paths = {"/video/**"};

        return GroupedOpenApi.builder()
                .group("Sample v1-video")
                .pathsToMatch(paths)
                .build();
    }
}
