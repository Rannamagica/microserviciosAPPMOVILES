package com.microservicios.Postulaciones.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservicios.Postulaciones.DTO.JobPostDTO;
import com.microservicios.Postulaciones.model.postulaciones;
import com.microservicios.Postulaciones.repository.PostulacionesRepository;


@Service
public class PostulacionesSevice {

    @Autowired
    private PostulacionesRepository appRepo;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_OFERTAS = "http://localhost:8082/api/posts";

    public postulaciones postular(Long userId, Long postId) {
        // Validar duplicados en MI base de datos
        if (appRepo.existsByUserIdAndPostId(userId, postId)) {
            throw new RuntimeException("Ya postulado");
        }
        postulaciones app = new postulaciones();
        app.setUserId(userId);
        app.setPostId(postId);
        return appRepo.save(app);
    }

    // Método que mezcla datos locales (IDs) con datos remotos (Detalles de Oferta)
    public List<JobPostDTO> obtenerMisPostulaciones(Long userId) {
        List<postulaciones> misApps = appRepo.findByUserId(userId);
        List<JobPostDTO> listaDetallada = new ArrayList<>();

        for (postulaciones app : misApps) {
            try {
                // LLAMADA HTTP: Pide los datos al Proyecto 2
                // GET http://localhost:8082/api/posts/{id}
                JobPostDTO oferta = restTemplate.getForObject(URL_OFERTAS + "/" + app.getPostId(), JobPostDTO.class);
                if (oferta != null) {
                    listaDetallada.add(oferta);
                }
            } catch (Exception e) {
                System.out.println("Error conectando con Ofertas: " + e.getMessage());
            }
        }
        return listaDetallada;
    }
}
