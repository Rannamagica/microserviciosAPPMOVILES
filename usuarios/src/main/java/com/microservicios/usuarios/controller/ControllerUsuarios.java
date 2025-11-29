package com.microservicios.usuarios.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.usuarios.DTO.AuthResponse;
import com.microservicios.usuarios.DTO.LoginRequest;
import com.microservicios.usuarios.DTO.RegisterRequest;
import com.microservicios.usuarios.model.usuarios;
import com.microservicios.usuarios.service.ServiceUsuarios;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/auth")
public class ControllerUsuarios {

    @Autowired
    private ServiceUsuarios userService;


    @Operation(
    summary = "Registrar un nuevo usuario",
    description = "Crea un usuario nuevo y retorna un token falso de registro."
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Usuario registrado correctamente",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AuthResponse.class)
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Error en los datos del registro",
        content = @Content(mediaType = "text/plain")
    )
})
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            // Llama al servicio (que ahora tiene los mensajes de consola)
            usuarios user = userService.register(req);

            // Crea la respuesta con el token falso
            AuthResponse respuesta = new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRol(),
                "TOKEN_FALSO_REGISTRO_123"
            );

            return ResponseEntity.ok(respuesta);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Valida las credenciales y retorna los datos del usuario con un token falso de login."
    )
        @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login exitoso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciales incorrectas",
            content = @Content(mediaType = "text/plain")
        )
    })
   @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        // 1. Buscamos al usuario
        Optional<usuarios> oUser = userService.login(req.email(), req.password());

        if (oUser.isPresent()) {
            usuarios user = oUser.get();

            // 2. CREAR RESPUESTA COMPLETA (Aquí faltaba el teléfono)
            AuthResponse respuesta = new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(), // <--- ¡ESTA ES LA LÍNEA QUE FALTABA!
                user.getRol(),
                "TOKEN_FALSO_LOGIN_999"
            );
            
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
    
   
}
