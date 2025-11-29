package com.microservicios.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.usuarios.model.usuarios;

public interface RepositoryUsuarios extends JpaRepository<usuarios, Long> {

    Optional<usuarios> findByEmail(String email);
    Boolean existsByEmail(String email);
    
}
