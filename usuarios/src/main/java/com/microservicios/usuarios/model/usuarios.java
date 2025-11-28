package com.microservicios.usuarios.model;

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
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 20) // Longitud opcional, pero recomendada para teléfonos
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rol;
}
