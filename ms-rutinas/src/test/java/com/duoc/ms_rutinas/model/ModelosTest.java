package com.duoc.ms_rutinas.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelosTest {

    @Test
    void objetivo_gettersYSetters_funcionanCorrectamente() {
        Objetivo objetivo = new Objetivo();
        objetivo.setId(1L);
        objetivo.setNombre("Pérdida de peso");
        objetivo.setDescripcion("Cardio + quema calórica");

        assertEquals(1L, objetivo.getId());
        assertEquals("Pérdida de peso", objetivo.getNombre());
        assertEquals("Cardio + quema calórica", objetivo.getDescripcion());
    }

    @Test
    void rutina_gettersYSetters_funcionanCorrectamente() {
        Objetivo objetivo = new Objetivo();
        objetivo.setId(1L);

        Rutina rutina = new Rutina();
        rutina.setId(1L);
        rutina.setNombre("Full Body Cardio");
        rutina.setObjetivo(objetivo);
        rutina.setNivel("Principiante");
        rutina.setDuracionEstimadaMin(12);

        LocalDateTime ahora = LocalDateTime.now();
        rutina.setFechaCreacion(ahora);

        List<Ejercicio> ejercicios = List.of(new Ejercicio());
        rutina.setEjercicios(ejercicios);

        assertEquals(1L, rutina.getId());
        assertEquals("Full Body Cardio", rutina.getNombre());
        assertEquals(objetivo, rutina.getObjetivo());
        assertEquals("Principiante", rutina.getNivel());
        assertEquals(12, rutina.getDuracionEstimadaMin());
        assertEquals(ahora, rutina.getFechaCreacion());
        assertEquals(1, rutina.getEjercicios().size());
    }

    @Test
    void rutina_fechaCreacionPorDefecto_noEsNula() {
        Rutina rutina = new Rutina();

        assertNotNull(rutina.getFechaCreacion());
    }

    @Test
    void ejercicio_gettersYSetters_funcionanCorrectamente() {
        Rutina rutina = new Rutina();
        rutina.setId(1L);

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(1L);
        ejercicio.setRutina(rutina);
        ejercicio.setNombre("Sentadillas");
        ejercicio.setDescripcion("Ejercicio de piernas");
        ejercicio.setDuracionSeg(30);
        ejercicio.setDescansoSeg(15);
        ejercicio.setOrden(1);
        ejercicio.setUrlAnimacion("https://ejemplo.com/animacion.gif");

        assertEquals(1L, ejercicio.getId());
        assertEquals(rutina, ejercicio.getRutina());
        assertEquals("Sentadillas", ejercicio.getNombre());
        assertEquals("Ejercicio de piernas", ejercicio.getDescripcion());
        assertEquals(30, ejercicio.getDuracionSeg());
        assertEquals(15, ejercicio.getDescansoSeg());
        assertEquals(1, ejercicio.getOrden());
        assertEquals("https://ejemplo.com/animacion.gif", ejercicio.getUrlAnimacion());
    }

    @Test
    void ejercicio_descansoSegPorDefecto_esCero() {
        Ejercicio ejercicio = new Ejercicio();

        assertEquals(0, ejercicio.getDescansoSeg());
    }
}