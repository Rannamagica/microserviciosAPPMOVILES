package com.microservicios.Postulaciones.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
public class postulaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // El ID del usuario que postula

    @Column(nullable = false)
    private Long postId; // El ID de la oferta a la que postula

    @CreationTimestamp
    private LocalDateTime applicationDate; // Fecha automática al guardar

}
