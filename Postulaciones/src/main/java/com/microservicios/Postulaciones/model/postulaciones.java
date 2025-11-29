package com.microservicios.Postulaciones.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa la postulación de un usuario a una oferta de trabajo")
public class postulaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "ID único de la postulación",
        example = "1001"
    )
    private Long id;

    @Column(nullable = false)
    @Schema(
        description = "ID del usuario que realiza la postulación",
        example = "12",
        required = true
    )
    private Long userId;

    @Column(nullable = false)
    @Schema(
        description = "ID de la oferta de trabajo a la que se postula",
        example = "45",
        required = true
    )
    private Long postId;

    @CreationTimestamp
    @Schema(
        description = "Fecha y hora en que se generó automáticamente la postulación",
        example = "2025-11-29T15:45:12"
    )
    private LocalDateTime applicationDate;

}
