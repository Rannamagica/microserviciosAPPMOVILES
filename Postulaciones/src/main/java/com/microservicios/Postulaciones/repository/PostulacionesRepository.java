package com.microservicios.Postulaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.Postulaciones.model.postulaciones;

public interface PostulacionesRepository extends JpaRepository<postulaciones, Long> {

   // Buscar todas las postulaciones de un usuario
    List<postulaciones> findByUserId(Long userId);
    
    // Evitar duplicados
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}
