package com.microservicios.Ofertas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactiva el bloqueo de formularios (necesario para Postman)
            .csrf(AbstractHttpConfigurer::disable)
            // Permite entrar a TODO sin contraseña
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            
        return http.build();
    }
}