package com.microservicios.Postulaciones.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO que representa una oferta de trabajo asociada a una postulación")
public class JobPostDTO {

    @Schema(description = "ID de la oferta de trabajo", example = "42")
    private Long id;

    @Schema(description = "Título del trabajo publicado", example = "Desarrollador Backend Java")
    private String titulo;

    @Schema(description = "Descripción breve del trabajo", example = "Responsable de crear APIs REST con Spring Boot")
    private String descripcion;

    @Schema(description = "Salario ofrecido para el puesto", example = "1200000")
    private Double salario;

    @Schema(description = "Nombre de la empresa que ofrece el empleo", example = "TechSolutions SPA")
    private String empresa;
}