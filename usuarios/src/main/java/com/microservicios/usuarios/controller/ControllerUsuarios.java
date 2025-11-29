package com.microservicios.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.usuarios.DTO.LoginRequest;
import com.microservicios.usuarios.DTO.RegisterRequest;
import com.microservicios.usuarios.service.ServiceUsuarios;


@RestController
@RequestMapping("/api/auth")
public class ControllerUsuarios {

    @Autowired
    private ServiceUsuarios userService;

    @PostMapping("/register")
    // ASEGÚRATE DE QUE ESTE @RequestBody SEA DE SPRING (org.springframework...)
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        System.out.println("INTENTANDO REGISTRAR: " + req.toString()); 
        try {
            return ResponseEntity.ok(userService.register(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return userService.login(req.email(), req.password())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
    
   
}
