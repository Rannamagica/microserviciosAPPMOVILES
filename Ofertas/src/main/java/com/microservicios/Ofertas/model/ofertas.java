package com.microservicios.Ofertas.model;

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
@Table(name = "job_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ofertas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos básicos de la oferta
    private String titulo;
    private String descripcion;
    private Double salario;
    private String experiencia; // Ej: "Senior", "Junior"
    private String carrera;     // Ej: "Informática"
    private String telefono;    // Teléfono de contacto

    // Relación lógica: ID del usuario que creó la oferta
    @Column(name = "id_reclutador")
    private Long idReclutador;
}
