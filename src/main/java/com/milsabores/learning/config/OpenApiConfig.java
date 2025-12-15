package com.milsabores.learning.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Pastelería Mil Sabores",
        version = "1.0.0",
        description = "Documentación de la API para gestión de productos, usuarios y pedidos."
    )
)
public class OpenApiConfig {
}