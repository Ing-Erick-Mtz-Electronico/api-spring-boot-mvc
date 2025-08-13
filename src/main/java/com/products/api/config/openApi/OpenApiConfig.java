package com.products.api.config.openApi;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Products API",
        version = "1.0.0",
        description = "API para gestión de productos"
    )
)
public class OpenApiConfig {
    
}
