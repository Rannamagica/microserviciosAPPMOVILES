package com.microservicios.Ofertas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.Ofertas.model.ofertas;
import com.microservicios.Ofertas.repository.RepositoryOfertas;

@Service
public class OfertasService {

    @Autowired
    private RepositoryOfertas jobRepo;

    // Crear una nueva oferta
    public ofertas crearOferta(ofertas post) {
        // Aquí podrías agregar validaciones (ej: que el salario sea positivo)
        return jobRepo.save(post);
    }

    // Obtener todas las ofertas (o filtrar por reclutador si llega el ID)
    public List<ofertas> listarOfertas(Long idReclutador) {
        if (idReclutador != null) {
            return jobRepo.findByIdReclutador(idReclutador);
        }
        return jobRepo.findAll();
    }

    // Obtener una oferta por su ID
    public ofertas obtenerOfertaPorId(Long id) {
        return jobRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("La oferta con ID " + id + " no existe."));
    }
}
