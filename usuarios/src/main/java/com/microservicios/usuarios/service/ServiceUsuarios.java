package com.microservicios.usuarios.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.usuarios.model.usuarios;
import com.microservicios.usuarios.repository.RepositoryUsuarios;

@Service
public class ServiceUsuarios {

    @Autowired
    private RepositoryUsuarios userRepository;

    public usuarios registrarUsuario(usuarios user) {
        // 1. Regla de negocio: Verificar si el email ya existe
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado: " + user.getEmail());
        }

        // 2. Regla de negocio: Aquí podrías encriptar la contraseña antes de guardar
        // user.setPassword(passwordEncoder.encode(user.getPassword())); 
        // (Por ahora lo guardamos tal cual para que funcione con tu app actual)

        return userRepository.save(user);
    }

    public Optional<usuarios> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Método para buscar por ID
    public Optional<usuarios> buscarPorId(Long id) {
        return userRepository.findById(id);
    }

}
