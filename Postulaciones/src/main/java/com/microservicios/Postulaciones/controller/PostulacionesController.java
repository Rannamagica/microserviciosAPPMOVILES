package com.microservicios.Postulaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservicios.Postulaciones.DTO.JobPostDTO;
import com.microservicios.Postulaciones.model.postulaciones;
import com.microservicios.Postulaciones.service.PostulacionesSevice;

@RestController
@RequestMapping("/api/applications")
public class PostulacionesController {

    @Autowired
    private PostulacionesSevice appService;

    public record RequestDTO(Long userId, Long postId) {}

    @PostMapping
    public ResponseEntity<?> apply(@RequestBody RequestDTO req) {
        try {
            return ResponseEntity.ok(appService.postular(req.userId(), req.postId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<JobPostDTO> getMyApplications(@PathVariable Long userId) {
        return appService.obtenerMisPostulaciones(userId);
    }



}
