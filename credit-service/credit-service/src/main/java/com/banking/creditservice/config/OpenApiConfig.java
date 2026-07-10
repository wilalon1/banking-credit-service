package com.banking.creditservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de documentación OpenAPI para Credit Service.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configure Swagger's general information.
     *
     * @return OpenAPI service configuration.
     */
    @Bean
    public OpenAPI creditServiceOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Credit Service API")
                        .description("REST API for credit and payment management")
                        .version("1.0.0"));
    }
}