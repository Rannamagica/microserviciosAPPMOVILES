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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/posts")
public class OfertasController {

    @Autowired
    private OfertasService jobService;

    // GET: Listar todas las ofertas
    // URL ejemplo: http://localhost:8081/api/posts
    // URL ejemplo filtro: http://localhost:8081/api/posts?idReclutador=1
    @Operation(
        summary = "Listar todas las ofertas",
        description = "Permite obtener todas las ofertas de trabajo. Opcionalmente se puede filtrar por el ID del reclutador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public List<ofertas> getAllPosts(@RequestParam(required = false) Long idReclutador) {
        return jobService.listarOfertas(idReclutador);
    }



    @Operation(
        summary = "Crear una nueva oferta laboral",
        description = "Crea una nueva oferta de trabajo usando los datos enviados en el cuerpo de la solicitud."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Oferta creada correctamente",
            content = @Content(schema = @Schema(implementation = ofertas.class))),
        @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
    })
    // POST: Crear una oferta
    @PostMapping
    public ResponseEntity<ofertas> createPost(@RequestBody ofertas post) {
        ofertas nuevaOferta = jobService.crearOferta(post);
        return ResponseEntity.ok(nuevaOferta);
    }



    @Operation(
        summary = "Obtener una oferta por ID",
        description = "Retorna los datos completos de una oferta específica según su ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Oferta encontrada",
            content = @Content(schema = @Schema(implementation = ofertas.class))),
        @ApiResponse(responseCode = "404", description = "Oferta no encontrada",
            content = @Content(mediaType = "text/plain"))
    })
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
