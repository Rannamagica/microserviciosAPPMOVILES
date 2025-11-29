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


@RestController
@RequestMapping("/api/auth")
public class ControllerUsuarios {

    @Autowired
    private ServiceUsuarios userService;

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
