package com.microservicios.usuarios.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicios.usuarios.DTO.RegisterRequest;
import com.microservicios.usuarios.model.usuarios;
import com.microservicios.usuarios.repository.RepositoryUsuarios;

@ExtendWith(MockitoExtension.class)
class ServiceUsuariosTest {

    @Mock
    private RepositoryUsuarios userRepository;

    @InjectMocks
    private ServiceUsuarios serviceUsuarios;

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
    void testRegister_Success() {
        // Given
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(usuarios.class))).thenReturn(usuario);

        // When
        usuarios result = serviceUsuarios.register(registerRequest);

        // Then
        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        assertEquals(usuario.getName(), result.getName());
        assertEquals(usuario.getEmail(), result.getEmail());
        verify(userRepository, times(1)).existsByEmail(registerRequest.getEmail());
        verify(userRepository, times(1)).save(any(usuarios.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        // Given
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceUsuarios.register(registerRequest);
        });

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(registerRequest.getEmail());
        verify(userRepository, never()).save(any(usuarios.class));
    }

    @Test
    void testLogin_Success() {
        // Given
        String email = "juan@test.com";
        String password = "Admin123!";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // When
        Optional<usuarios> result = serviceUsuarios.login(email, password);

        // Then
        assertTrue(result.isPresent());
        assertEquals(usuario.getEmail(), result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testLogin_WrongPassword() {
        // Given
        String email = "juan@test.com";
        String wrongPassword = "WrongPassword";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // When
        Optional<usuarios> result = serviceUsuarios.login(email, wrongPassword);

        // Then
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testLogin_UserNotFound() {
        // Given
        String email = "noexiste@test.com";
        String password = "Admin123!";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<usuarios> result = serviceUsuarios.login(email, password);

        // Then
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }
}
