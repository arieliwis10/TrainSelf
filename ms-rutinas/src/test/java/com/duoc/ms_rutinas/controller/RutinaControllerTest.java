package com.duoc.ms_rutinas.controller;

import com.duoc.ms_rutinas.model.Rutina;
import com.duoc.ms_rutinas.service.RoutineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RutinaControllerTest {

    @Mock
    private RoutineService routineService;

    @InjectMocks
    private RutinaController rutinaController;

    @Test
    void listar_conObjetivoYNivel_delegaAlServicio() {
        Rutina rutina = new Rutina();
        rutina.setNombre("Full Body Cardio");
        when(routineService.filtrarPorObjetivoYNivel("Pérdida de peso", "Principiante"))
                .thenReturn(List.of(rutina));

        ResponseEntity<List<Rutina>> respuesta = rutinaController.listar("Pérdida de peso", "Principiante");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        verify(routineService).filtrarPorObjetivoYNivel("Pérdida de peso", "Principiante");
    }

    @Test
    void listar_sinResultados_retornaListaVacia() {
        when(routineService.filtrarPorObjetivoYNivel(anyString(), anyString()))
                .thenReturn(List.of());

        ResponseEntity<List<Rutina>> respuesta = rutinaController.listar("Resistencia", "Avanzado");

        assertTrue(respuesta.getBody().isEmpty());
    }

    @Test
    void obtener_rutinaExistente_retorna200ConLaRutina() {
        Rutina rutina = new Rutina();
        rutina.setId(1L);
        rutina.setNombre("Full Body Cardio");
        when(routineService.obtenerPorId(1L)).thenReturn(rutina);

        ResponseEntity<Rutina> respuesta = rutinaController.obtener(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Full Body Cardio", respuesta.getBody().getNombre());
    }

    @Test
    void obtener_rutinaInexistente_propagaLaExcepcionDelServicio() {
        when(routineService.obtenerPorId(999L))
                .thenThrow(new RuntimeException("Rutina no encontrada: 999"));

        assertThrows(RuntimeException.class, () -> rutinaController.obtener(999L));
    }
}