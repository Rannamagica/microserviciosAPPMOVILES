package com.microservicios.Ofertas.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.Ofertas.model.ofertas;
import com.microservicios.Ofertas.service.OfertasService;

@WebMvcTest(OfertasController.class)
class OfertasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfertasService jobService;

    @Autowired
    private ObjectMapper objectMapper;

    private ofertas oferta1;
    private ofertas oferta2;

    @BeforeEach
    void setUp() {
        oferta1 = new ofertas();
        oferta1.setId(1L);
        oferta1.setTitulo("Desarrollador Full Stack");
        oferta1.setDescripcion("Buscamos desarrollador con experiencia");
        oferta1.setSalario(1500000.0);
        oferta1.setExperiencia("Senior");
        oferta1.setCarrera("Ingeniería Informática");
        oferta1.setTelefono("+56912345678");
        oferta1.setIdReclutador(10L);

        oferta2 = new ofertas();
        oferta2.setId(2L);
        oferta2.setTitulo("Diseñador UX/UI");
        oferta2.setDescripcion("Buscamos diseñador creativo");
        oferta2.setSalario(1200000.0);
        oferta2.setExperiencia("Junior");
        oferta2.setCarrera("Diseño Gráfico");
        oferta2.setTelefono("+56987654321");
        oferta2.setIdReclutador(10L);
    }

    @Test
    void testGetAllPosts_SinFiltro() throws Exception {
        // Given
        List<ofertas> listaOfertas = Arrays.asList(oferta1, oferta2);
        when(jobService.listarOfertas(null)).thenReturn(listaOfertas);

        // When & Then
        mockMvc.perform(get("/api/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Desarrollador Full Stack"))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(jobService, times(1)).listarOfertas(null);
    }

    @Test
    void testGetAllPosts_ConFiltroReclutador() throws Exception {
        // Given
        Long idReclutador = 10L;
        List<ofertas> listaOfertas = Arrays.asList(oferta1, oferta2);
        when(jobService.listarOfertas(idReclutador)).thenReturn(listaOfertas);

        // When & Then
        mockMvc.perform(get("/api/posts")
                .param("idReclutador", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(jobService, times(1)).listarOfertas(idReclutador);
    }

    @Test
    void testCreatePost_Success() throws Exception {
        // Given
        when(jobService.crearOferta(any(ofertas.class))).thenReturn(oferta1);

        // When & Then
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(oferta1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Desarrollador Full Stack"))
                .andExpect(jsonPath("$.salario").value(1500000.0));

        verify(jobService, times(1)).crearOferta(any(ofertas.class));
    }

    @Test
    void testGetPostById_Success() throws Exception {
        // Given
        Long id = 1L;
        when(jobService.obtenerOfertaPorId(id)).thenReturn(oferta1);

        // When & Then
        mockMvc.perform(get("/api/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Desarrollador Full Stack"));

        verify(jobService, times(1)).obtenerOfertaPorId(id);
    }

    @Test
    void testGetPostById_NotFound() throws Exception {
        // Given
        Long id = 999L;
        when(jobService.obtenerOfertaPorId(id))
                .thenThrow(new RuntimeException("La oferta con ID " + id + " no existe."));

        // When & Then
        mockMvc.perform(get("/api/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La oferta con ID " + id + " no existe."));

        verify(jobService, times(1)).obtenerOfertaPorId(id);
    }
}
