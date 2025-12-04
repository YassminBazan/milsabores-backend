package com.milsabores.learning.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; // Importante para CORS
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactivar CSRF (No necesario para APIs REST stateless)
            .csrf(csrf -> csrf.disable())
            
            // 2. Configurar CORS (Permitir conexión desde React)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. Configurar Rutas
            .authorizeHttpRequests(auth -> auth
                // Rutas Públicas
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/productos/**").permitAll()
                
                // --- AGREGA ESTAS DOS LÍNEAS ---
                .requestMatchers("/pedidos/**").permitAll()  // Permitir crear y ver pedidos
                .requestMatchers("/usuarios/**").permitAll() // Permitir admin de usuarios
                // -------------------------------
                
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
            )
            
            // 4. No guardar sesión en memoria (Stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            
            // (Más adelante aquí añadiremos el filtro de JWT)

        return http.build();
    }

    // Configuración Global de CORS para Spring Security
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    // Bean para encriptar contraseñas (Usado en AuthController)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}