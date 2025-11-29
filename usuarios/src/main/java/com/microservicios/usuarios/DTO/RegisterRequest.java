package com.microservicios.usuarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String rol;

    // Constructor vacío (Obligatorio para JSON)
    public RegisterRequest() {}

    // Constructor con todo
    public RegisterRequest(String name, String email, String phone, String password, String rol) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.rol = rol;
    }

    // GETTERS Y SETTERS MANUALES
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
        return "RegisterRequest{email='" + email + "', name='" + name + "'}";
    }
}