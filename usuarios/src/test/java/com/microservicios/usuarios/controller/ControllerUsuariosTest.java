package com.microservicios.usuarios.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.usuarios.DTO.LoginRequest;
import com.microservicios.usuarios.DTO.RegisterRequest;
import com.microservicios.usuarios.config.TestSecurityConfig;
import com.microservicios.usuarios.model.usuarios;
import com.microservicios.usuarios.service.ServiceUsuarios;

@WebMvcTest(ControllerUsuarios.class)
@Import(TestSecurityConfig.class)
class ControllerUsuariosTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceUsuarios userService;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterRequest registerRequest;
    private usuarios usuario;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setName("Juan Pérez");
        registerRequest.setEmail("juan@test.com");
        registerRequest.setPhone("+56912345678");
        registerRequest.setPassword("Admin123!");
        registerRequest.setRol("Colaborador");

        usuario = new usuarios();
        usuario.setId(1L);
        usuario.setName("Juan Pérez");
        usuario.setEmail("juan@test.com");
        usuario.setPhone("+56912345678");
        usuario.setPassword("Admin123!");
        usuario.setRol("Colaborador");
    }

    @Test
    void testRegister_Success() throws Exception {
        // Entregados
        when(userService.register(any(RegisterRequest.class))).thenReturn(usuario);

        // cuando y luego 
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@test.com"))
                .andExpect(jsonPath("$.token").value("TOKEN_FALSO_REGISTRO_123"));

        verify(userService, times(1)).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() throws Exception {
        
        when(userService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("El email ya está registrado"));

        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El email ya está registrado"));

        verify(userService, times(1)).register(any(RegisterRequest.class));
    }

    @Test
    void testLogin_Success() throws Exception {
        
        LoginRequest loginRequest = new LoginRequest("juan@test.com", "Admin123!");
        when(userService.login(anyString(), anyString())).thenReturn(Optional.of(usuario));

        
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@test.com"))
                .andExpect(jsonPath("$.token").value("TOKEN_FALSO_LOGIN_999"));

        verify(userService, times(1)).login(anyString(), anyString());
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        
        LoginRequest loginRequest = new LoginRequest("juan@test.com", "WrongPassword");
        when(userService.login(anyString(), anyString())).thenReturn(Optional.empty());

        
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales incorrectas"));

        verify(userService, times(1)).login(anyString(), anyString());
    }
}
