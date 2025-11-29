package com.microservicios.usuarios.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.usuarios.DTO.RegisterRequest;
import com.microservicios.usuarios.model.usuarios;
import com.microservicios.usuarios.repository.RepositoryUsuarios;




@Service
public class ServiceUsuarios {
    

    @Autowired
    private RepositoryUsuarios userRepository;

    public usuarios register(RegisterRequest req) {
        // 1. Debug: Ver en consola qué llega
        System.out.println("SERVICIO INTENTANDO GUARDAR: " + req.toString());

        // 2. Validación
        if (req.getEmail() == null || req.getEmail().isEmpty()) {
            throw new RuntimeException("ERROR: El email llegó vacío al servicio");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 3. Crear la entidad (Usando tu clase 'Usuarios')
        usuarios nuevoUsuario = new usuarios();

        // 4. Mapeo MANUAL (Aquí aseguramos que no se pierda nada)
        nuevoUsuario.setName(req.getName());
        nuevoUsuario.setEmail(req.getEmail()); // <--- ESTO ES LO CRÍTICO
        nuevoUsuario.setPhone(req.getPhone());
        nuevoUsuario.setPassword(req.getPassword());
        nuevoUsuario.setRol(req.getRol());

        // 5. Guardar
        return userRepository.save(nuevoUsuario);
    }

    public Optional<usuarios> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }
    
}
