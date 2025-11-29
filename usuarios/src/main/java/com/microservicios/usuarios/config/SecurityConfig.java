package com.microservicios.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. ESTA LÍNEA ES LA CLAVE: Desactiva el bloqueo para Postman/Android
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Permite entrar a TODOS los endpoints sin contraseña
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
            
        return http.build();
    }
}
