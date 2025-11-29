package com.microservicios.usuarios.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {

    // @JsonProperty OBLIGA a Java a conectar con el JSON
    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("password")
    private String password;

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