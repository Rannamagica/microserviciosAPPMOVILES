package com.microservicios.Ofertas.model;

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
@Table(name = "job_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo que representa una oferta de trabajo publicada por un reclutador.")
public class ofertas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la oferta", example = "10")
    private Long id;

    @Schema(description = "Título de la oferta de trabajo", example = "Desarrollador Full Stack")
    private String titulo;

    @Schema(description = "Descripción detallada de la oferta", 
            example = "Buscamos un desarrollador con experiencia en Java y React.")
    private String descripcion;

    @Schema(description = "Salario ofrecido para el puesto", example = "1500000")
    private Double salario;

    @Schema(description = "Nivel de experiencia requerido", 
            example = "Junior / Semi Senior / Senior")
    private String experiencia;

    @Schema(description = "Carrera o área profesional requerida", 
            example = "Ingeniería en Informática")
    private String carrera;

    @Schema(description = "Teléfono de contacto del reclutador", 
            example = "+56912345678")
    private String telefono;

    @Column(name = "id_reclutador")
    @Schema(description = "ID del usuario que creó la oferta", example = "3")
    private Long idReclutador;
}
