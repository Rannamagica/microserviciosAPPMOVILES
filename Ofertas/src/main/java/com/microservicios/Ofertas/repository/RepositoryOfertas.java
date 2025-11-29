package com.microservicios.Ofertas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.Ofertas.model.ofertas;


public interface RepositoryOfertas extends JpaRepository<ofertas, Long> {
    List<ofertas> findByIdReclutador(Long idReclutador);
}
