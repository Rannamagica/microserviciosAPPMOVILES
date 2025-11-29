package com.microservicios.usuarios.DTO;

public class AuthResponse {
    
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String rol;
    private String token; 

    // --- ESTE ES EL ÚNICO CONSTRUCTOR QUE DEBES TENER ---
    public AuthResponse(Long id, String name, String email, String phone, String rol, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.rol = rol;
        this.token = token;
    }
    // ----------------------------------------------------

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
