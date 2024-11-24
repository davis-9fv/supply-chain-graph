package com.supplychaingraph.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun info(): GroupedOpenApi =
        GroupedOpenApi
            .builder()
            .group("Supply-Chain-Graph")
            .pathsToMatch("/api/v1/**")
            .build()

    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Tree Management API")
                    .description("API for managing tree structures with nodes and edges.")
                    .version("1.0.0")
                    .license(License().name("Apache 2.0").url("http://springdoc.org")),
            )
}
