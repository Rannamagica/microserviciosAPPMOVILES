package com.microservicios.usuarios.model;

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
@Table(name = "users") // Coincide con tableName = "users"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class usuarios {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID único autogenerado", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String name;

    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electrónico (debe ser único)", example = "juan@workeando.com")
    private String email;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String phone;
    
    @Column(nullable = false)
    @Schema(description = "Contraseña encriptada", example = "Admin123!")
    private String password; // Recuerda: En producción esto debe ser HASH

    @Column(nullable = false) // "Reclutador" o "Colaborador"
    @Schema(description = "Rol del usuario en la plataforma", example = "Colaborador", allowableValues = {"Reclutador", "Colaborador"})
    private String rol;
}
