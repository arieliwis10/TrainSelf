package com.duoc.ms_rutinas.service;

import com.duoc.ms_rutinas.model.Objetivo;
import com.duoc.ms_rutinas.model.Rutina;
import com.duoc.ms_rutinas.repository.RutinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoutineServiceTest {

    @Mock
    private RutinaRepository rutinaRepository;

    @InjectMocks
    private RoutineService routineService;

    private Rutina rutinaEjemplo;

    @BeforeEach
    void setUp() {
        Objetivo objetivo = new Objetivo();
        objetivo.setId(1L);
        objetivo.setNombre("Pérdida de peso");

        rutinaEjemplo = new Rutina();
        rutinaEjemplo.setId(1L);
        rutinaEjemplo.setNombre("Full Body Cardio");
        rutinaEjemplo.setObjetivo(objetivo);
        rutinaEjemplo.setNivel("Principiante");
        rutinaEjemplo.setDuracionEstimadaMin(12);
    }

    @Test
    void filtrarPorObjetivoYNivel_conNivelEspecificado_llamaAlMetodoCorrecto() {
        when(rutinaRepository.findByObjetivo_NombreAndNivel("Pérdida de peso", "Principiante"))
                .thenReturn(List.of(rutinaEjemplo));

        List<Rutina> resultado = routineService.filtrarPorObjetivoYNivel("Pérdida de peso", "Principiante");

        assertEquals(1, resultado.size());
        assertEquals("Full Body Cardio", resultado.get(0).getNombre());
        verify(rutinaRepository).findByObjetivo_NombreAndNivel("Pérdida de peso", "Principiante");
        verify(rutinaRepository, never()).findByObjetivo_Nombre(anyString());
    }

    @Test
    void filtrarPorObjetivoYNivel_sinNivel_buscaSoloPorObjetivo() {
        when(rutinaRepository.findByObjetivo_Nombre("Resistencia"))
                .thenReturn(List.of(rutinaEjemplo));

        List<Rutina> resultado = routineService.filtrarPorObjetivoYNivel("Resistencia", null);

        assertEquals(1, resultado.size());
        verify(rutinaRepository).findByObjetivo_Nombre("Resistencia");
        verify(rutinaRepository, never()).findByObjetivo_NombreAndNivel(anyString(), anyString());
    }

    @Test
    void filtrarPorObjetivoYNivel_conNivelVacio_buscaSoloPorObjetivo() {
        when(rutinaRepository.findByObjetivo_Nombre("Resistencia"))
                .thenReturn(List.of(rutinaEjemplo));

        List<Rutina> resultado = routineService.filtrarPorObjetivoYNivel("Resistencia", "  ");

        assertEquals(1, resultado.size());
        verify(rutinaRepository).findByObjetivo_Nombre("Resistencia");
    }

    @Test
    void filtrarPorObjetivoYNivel_sinResultados_retornaListaVacia() {
        when(rutinaRepository.findByObjetivo_NombreAndNivel(anyString(), anyString()))
                .thenReturn(List.of());

        List<Rutina> resultado = routineService.filtrarPorObjetivoYNivel("Ganancia muscular", "Avanzado");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarTodas_retornaTodasLasRutinas() {
        when(rutinaRepository.findAll()).thenReturn(List.of(rutinaEjemplo));

        List<Rutina> resultado = routineService.listarTodas();

        assertEquals(1, resultado.size());
        verify(rutinaRepository).findAll();
    }

    @Test
    void obtenerPorId_existente_retornaLaRutina() {
        when(rutinaRepository.findById(1L)).thenReturn(Optional.of(rutinaEjemplo));

        Rutina resultado = routineService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Full Body Cardio", resultado.getNombre());
    }

    @Test
    void obtenerPorId_inexistente_lanzaExcepcion() {
        when(rutinaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> routineService.obtenerPorId(99L));

        assertTrue(ex.getMessage().contains("99"));
    }
}