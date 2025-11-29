package com.microservicios.Postulaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.Postulaciones.DTO.JobPostDTO;
import com.microservicios.Postulaciones.service.PostulacionesSevice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/applications")
public class PostulacionesController {


    @Autowired
    private PostulacionesSevice appService;


    @Schema(description = "DTO para postular a un trabajo")
    public record RequestDTO(Long userId, Long postId) {}


         @Operation(
        summary = "Postular a un trabajo",
        description = "Permite que un usuario postule a un post de trabajo del sistema."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Postulación realizada correctamente",
        content = @Content(
            mediaType = "application/json"
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "Error al postular",
        content = @Content(mediaType = "text/plain")
    )
    @PostMapping
    public ResponseEntity<?> apply(@RequestBody RequestDTO req) {
        try {
            return ResponseEntity.ok(appService.postular(req.userId(), req.postId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(
        summary = "Obtener todas las postulaciones de un usuario",
        description = "Devuelve la lista de trabajos a los que el usuario ha postulado."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de postulaciones obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = JobPostDTO.class)
        )
    )
    @GetMapping("/user/{userId}")
    public List<JobPostDTO> getMyApplications(@PathVariable Long userId) {
        return appService.obtenerMisPostulaciones(userId);
    }



}
