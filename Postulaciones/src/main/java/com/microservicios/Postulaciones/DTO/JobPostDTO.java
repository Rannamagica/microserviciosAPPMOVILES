package com.microservicios.Postulaciones.DTO;
import lombok.Data;

@Data // Sin @Entity, solo datos
public class JobPostDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Double salario;
    private String empresa;
    
}