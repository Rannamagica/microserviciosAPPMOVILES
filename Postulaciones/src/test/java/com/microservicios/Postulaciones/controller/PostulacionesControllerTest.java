package com.microservicios.Postulaciones.controller;

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
import com.microservicios.Postulaciones.DTO.JobPostDTO;
import com.microservicios.Postulaciones.controller.PostulacionesController.RequestDTO;
import com.microservicios.Postulaciones.model.postulaciones;
import com.microservicios.Postulaciones.service.PostulacionesSevice;

@WebMvcTest(PostulacionesController.class)
class PostulacionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostulacionesSevice appService;

    @Autowired
    private ObjectMapper objectMapper;

    private postulaciones postulacion;
    private JobPostDTO jobPostDTO1;
    private JobPostDTO jobPostDTO2;

    @BeforeEach
    void setUp() {
        postulacion = new postulaciones();
        postulacion.setId(1L);
        postulacion.setUserId(100L);
        postulacion.setPostId(200L);

        jobPostDTO1 = new JobPostDTO();
        jobPostDTO1.setId(200L);
        jobPostDTO1.setTitulo("Desarrollador Full Stack");
        jobPostDTO1.setDescripcion("Desarrollo de aplicaciones web");
        jobPostDTO1.setSalario(1500000.0);

        jobPostDTO2 = new JobPostDTO();
        jobPostDTO2.setId(201L);
        jobPostDTO2.setTitulo("Diseñador UX/UI");
        jobPostDTO2.setDescripcion("Diseño de interfaces");
        jobPostDTO2.setSalario(1200000.0);
    }

    @Test
    void testApply_Success() throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO(100L, 200L);
        when(appService.postular(anyLong(), anyLong())).thenReturn(postulacion);

        // When & Then
        mockMvc.perform(post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(100))
                .andExpect(jsonPath("$.postId").value(200));

        verify(appService, times(1)).postular(anyLong(), anyLong());
    }

    @Test
    void testApply_YaPostulado() throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO(100L, 200L);
        when(appService.postular(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Ya postulado"));

        // When & Then
        mockMvc.perform(post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ya postulado"));

        verify(appService, times(1)).postular(anyLong(), anyLong());
    }

    @Test
    void testGetMyApplications_Success() throws Exception {
        // Given
        Long userId = 100L;
        List<JobPostDTO> listaOfertas = Arrays.asList(jobPostDTO1, jobPostDTO2);
        when(appService.obtenerMisPostulaciones(userId)).thenReturn(listaOfertas);

        // When & Then
        mockMvc.perform(get("/api/applications/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(200))
                .andExpect(jsonPath("$[0].titulo").value("Desarrollador Full Stack"))
                .andExpect(jsonPath("$[1].id").value(201))
                .andExpect(jsonPath("$[1].titulo").value("Diseñador UX/UI"));

        verify(appService, times(1)).obtenerMisPostulaciones(userId);
    }

    @Test
    void testGetMyApplications_EmptyList() throws Exception {
        // Given
        Long userId = 100L;
        when(appService.obtenerMisPostulaciones(userId)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/applications/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(appService, times(1)).obtenerMisPostulaciones(userId);
    }
}
