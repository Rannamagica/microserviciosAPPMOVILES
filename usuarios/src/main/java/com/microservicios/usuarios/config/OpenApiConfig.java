package com.microservicios.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Usuarios Microservice API")
                                .version("1.0")
                                .description("Microservicio encargado del Registro, Login y gestión de perfiles de candidatos y reclutadores. ")

                );
    }

}
