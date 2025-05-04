package com.terzicaglar.socialnetwork.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Social Network API",
                version = "1.0.0",
                description = "API for managing users, visits, likes, and fraud detection."
        )
)
public class OpenApiDocumentationConfig {
}
