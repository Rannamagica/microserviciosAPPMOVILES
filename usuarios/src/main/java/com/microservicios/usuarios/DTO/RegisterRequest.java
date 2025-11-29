package com.microservicios.usuarios.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para registrar un nuevo usuario en el sistema")
public class RegisterRequest {

    @Schema(description = "Nombre completo del usuario", example = "Felipe Arriagada")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "felipe@gmail.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Número telefónico del usuario", example = "+56 9 7777 1234")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "Contraseña del usuario", example = "123456")
    @JsonProperty("password")
    private String password;

    @Schema(description = "Rol del usuario dentro del sistema", example = "cliente")
    @JsonProperty("rol")
    private String rol;

    public RegisterRequest() {}

    public RegisterRequest(String name, String email, String phone, String password, String rol) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.rol = rol;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "RegisterRequest [name=" + name + ", email=" + email + "]";
    }
}