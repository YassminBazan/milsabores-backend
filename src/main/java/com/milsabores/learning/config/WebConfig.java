package com.milsabores.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        // 1. Buscamos la carpeta "imagenes" en el directorio actual
        Path rutaImagenes = Paths.get("img");
        
        // 2. TRUCO: Convertimos a URI nativa del sistema
        // Esto genera automáticamente "file:///C:/Users/..." correcto para tu Windows
        String rutaUri = rutaImagenes.toUri().toString();

        // 3. DEBUG: ¡Esto imprimirá la ruta en tu consola al arrancar!
        System.out.println("📂 CONFIGURACIÓN DE IMÁGENES:");
        System.out.println("   👉 URL Web: /img/**");
        System.out.println("   👉 Carpeta Física: " + rutaUri);

        // 4. Configuramos el handler
        registry.addResourceHandler("/img/**")
                .addResourceLocations(rutaUri);
    }
}