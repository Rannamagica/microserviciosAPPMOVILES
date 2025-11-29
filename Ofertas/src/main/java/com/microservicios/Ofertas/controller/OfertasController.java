package com.microservicios.Ofertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.Ofertas.model.ofertas;
import com.microservicios.Ofertas.service.OfertasService;

@RestController
@RequestMapping("/api/posts")
public class OfertasController {

    @Autowired
    private OfertasService jobService;

    // GET: Listar todas las ofertas
    // URL ejemplo: http://localhost:8081/api/posts
    // URL ejemplo filtro: http://localhost:8081/api/posts?idReclutador=1
    @GetMapping
    public List<ofertas> getAllPosts(@RequestParam(required = false) Long idReclutador) {
        return jobService.listarOfertas(idReclutador);
    }

    // POST: Crear una oferta
    @PostMapping
    public ResponseEntity<ofertas> createPost(@RequestBody ofertas post) {
        ofertas nuevaOferta = jobService.crearOferta(post);
        return ResponseEntity.ok(nuevaOferta);
    }

    // GET: Ver detalle de una oferta por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            ofertas oferta = jobService.obtenerOfertaPorId(id);
            return ResponseEntity.ok(oferta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
