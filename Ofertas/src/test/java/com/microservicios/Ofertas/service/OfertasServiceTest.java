package com.microservicios.Ofertas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicios.Ofertas.model.ofertas;
import com.microservicios.Ofertas.repository.RepositoryOfertas;

@ExtendWith(MockitoExtension.class)
class OfertasServiceTest {

    @Mock
    private RepositoryOfertas jobRepo;

    @InjectMocks
    private OfertasService ofertasService;

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
    void testCrearOferta_Success() {
        // Given
        when(jobRepo.save(any(ofertas.class))).thenReturn(oferta1);

        // When
        ofertas result = ofertasService.crearOferta(oferta1);

        // Then
        assertNotNull(result);
        assertEquals(oferta1.getId(), result.getId());
        assertEquals(oferta1.getTitulo(), result.getTitulo());
        verify(jobRepo, times(1)).save(any(ofertas.class));
    }

    @Test
    void testListarOfertas_SinFiltro() {
        // Given
        List<ofertas> listaOfertas = Arrays.asList(oferta1, oferta2);
        when(jobRepo.findAll()).thenReturn(listaOfertas);

        // When
        List<ofertas> result = ofertasService.listarOfertas(null);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jobRepo, times(1)).findAll();
        verify(jobRepo, never()).findByIdReclutador(anyLong());
    }

    @Test
    void testListarOfertas_ConFiltroReclutador() {
        // Given
        Long idReclutador = 10L;
        List<ofertas> listaOfertas = Arrays.asList(oferta1, oferta2);
        when(jobRepo.findByIdReclutador(idReclutador)).thenReturn(listaOfertas);

        // When
        List<ofertas> result = ofertasService.listarOfertas(idReclutador);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jobRepo, times(1)).findByIdReclutador(idReclutador);
        verify(jobRepo, never()).findAll();
    }

    @Test
    void testObtenerOfertaPorId_Success() {
        // Given
        Long id = 1L;
        when(jobRepo.findById(id)).thenReturn(Optional.of(oferta1));

        // When
        ofertas result = ofertasService.obtenerOfertaPorId(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(oferta1.getTitulo(), result.getTitulo());
        verify(jobRepo, times(1)).findById(id);
    }

    @Test
    void testObtenerOfertaPorId_NotFound() {
        // Given
        Long id = 999L;
        when(jobRepo.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ofertasService.obtenerOfertaPorId(id);
        });

        assertEquals("La oferta con ID " + id + " no existe.", exception.getMessage());
        verify(jobRepo, times(1)).findById(id);
    }
}
