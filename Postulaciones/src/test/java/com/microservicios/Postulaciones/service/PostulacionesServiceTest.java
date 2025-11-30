package com.microservicios.Postulaciones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.microservicios.Postulaciones.DTO.JobPostDTO;
import com.microservicios.Postulaciones.model.postulaciones;
import com.microservicios.Postulaciones.repository.PostulacionesRepository;

@ExtendWith(MockitoExtension.class)
class PostulacionesServiceTest {

    @Mock
    private PostulacionesRepository appRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PostulacionesSevice postulacionesService;

    private postulaciones postulacion1;
    private postulaciones postulacion2;
    private JobPostDTO jobPostDTO;

    @BeforeEach
    void setUp() {
        postulacion1 = new postulaciones();
        postulacion1.setId(1L);
        postulacion1.setUserId(100L);
        postulacion1.setPostId(200L);

        postulacion2 = new postulaciones();
        postulacion2.setId(2L);
        postulacion2.setUserId(100L);
        postulacion2.setPostId(201L);

        jobPostDTO = new JobPostDTO();
        jobPostDTO.setId(200L);
        jobPostDTO.setTitulo("Desarrollador Full Stack");
        jobPostDTO.setDescripcion("Desarrollo de aplicaciones web");
        jobPostDTO.setSalario(1500000.0);
    }

    @Test
    void testPostular_Success() {
        // Given
        Long userId = 100L;
        Long postId = 200L;
        when(appRepo.existsByUserIdAndPostId(userId, postId)).thenReturn(false);
        when(appRepo.save(any(postulaciones.class))).thenReturn(postulacion1);

        // When
        postulaciones result = postulacionesService.postular(userId, postId);

        // Then
        assertNotNull(result);
        assertEquals(postulacion1.getId(), result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(postId, result.getPostId());
        verify(appRepo, times(1)).existsByUserIdAndPostId(userId, postId);
        verify(appRepo, times(1)).save(any(postulaciones.class));
    }

    @Test
    void testPostular_YaPostulado() {
        // Given
        Long userId = 100L;
        Long postId = 200L;
        when(appRepo.existsByUserIdAndPostId(userId, postId)).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postulacionesService.postular(userId, postId);
        });

        assertEquals("Ya postulado", exception.getMessage());
        verify(appRepo, times(1)).existsByUserIdAndPostId(userId, postId);
        verify(appRepo, never()).save(any(postulaciones.class));
    }

    @Test
    void testObtenerMisPostulaciones_Success() {
        // Given
        Long userId = 100L;
        List<postulaciones> listaPostulaciones = Arrays.asList(postulacion1, postulacion2);
        when(appRepo.findByUserId(userId)).thenReturn(listaPostulaciones);
        when(restTemplate.getForObject(anyString(), eq(JobPostDTO.class))).thenReturn(jobPostDTO);

        // When
        List<JobPostDTO> result = postulacionesService.obtenerMisPostulaciones(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(appRepo, times(1)).findByUserId(userId);
        verify(restTemplate, times(2)).getForObject(anyString(), eq(JobPostDTO.class));
    }

    @Test
    void testObtenerMisPostulaciones_ErrorConexion() {
        // Given
        Long userId = 100L;
        List<postulaciones> listaPostulaciones = Arrays.asList(postulacion1);
        when(appRepo.findByUserId(userId)).thenReturn(listaPostulaciones);
        when(restTemplate.getForObject(anyString(), eq(JobPostDTO.class)))
                .thenThrow(new RuntimeException("Error de conexión"));

        // When
        List<JobPostDTO> result = postulacionesService.obtenerMisPostulaciones(userId);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size()); // No se agregaron ofertas debido al error
        verify(appRepo, times(1)).findByUserId(userId);
    }

    @Test
    void testObtenerMisPostulaciones_OfertaNull() {
        // Given
        Long userId = 100L;
        List<postulaciones> listaPostulaciones = Arrays.asList(postulacion1);
        when(appRepo.findByUserId(userId)).thenReturn(listaPostulaciones);
        when(restTemplate.getForObject(anyString(), eq(JobPostDTO.class))).thenReturn(null);

        // When
        List<JobPostDTO> result = postulacionesService.obtenerMisPostulaciones(userId);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size()); // No se agregaron ofertas porque retornó null
        verify(appRepo, times(1)).findByUserId(userId);
    }
}
