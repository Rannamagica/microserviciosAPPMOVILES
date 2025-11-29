package com.microservicios.usuarios.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta enviada después de registrar o iniciar sesión")
public class AuthResponse {
    
    @Schema(description = "ID del usuario", example = "12")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Felipe Arriagada")
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "felipe@gmail.com")
    private String email;

    @Schema(description = "Número de teléfono del usuario", example = "+56 9 7777 1234")
    private String phone;

    @Schema(description = "Rol asignado al usuario", example = "cliente")
    private String rol;

    @Schema(description = "Token JWT generado para la sesión", example = "TOKEN_FALSO_LOGIN_999")
    private String token; 

    // --- Constructor requerido ---
    public AuthResponse(Long id, String name, String email, String phone, String rol, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.rol = rol;
        this.token = token;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}