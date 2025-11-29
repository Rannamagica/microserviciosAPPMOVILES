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
        System.out.println("--- SERVICIO: INICIANDO PROCESO DE GUARDADO ---");
        System.out.println("Datos recibidos: " + req.toString());

        // 1. Validar duplicados
        if (userRepository.existsByEmail(req.getEmail())) {
            System.out.println("--- ERROR: EL EMAIL YA EXISTE ---");
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Mapear datos (Pasar de DTO a Entidad)
        usuarios nuevoUsuario = new usuarios();
        nuevoUsuario.setName(req.getName());
        nuevoUsuario.setEmail(req.getEmail());
        nuevoUsuario.setPhone(req.getPhone());
        nuevoUsuario.setPassword(req.getPassword());
        nuevoUsuario.setRol(req.getRol());

        // 3. GUARDAR (La línea más importante)
        System.out.println("--- SERVICIO: INTENTANDO GUARDAR EN MYSQL... ---");
        usuarios usuarioGuardado = userRepository.save(nuevoUsuario);
        
        System.out.println("--- SERVICIO: ¡GUARDADO CON ID " + usuarioGuardado.getId() + "! ---");
        
        return usuarioGuardado;
    }

    public Optional<usuarios> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }
    
}
