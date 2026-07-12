package com.duoc.ms_rutinas.controller;

import com.duoc.ms_rutinas.model.Rutina;
import com.duoc.ms_rutinas.service.RoutineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutinas")
public class RutinaController {

    private final RoutineService routineService;

    public RutinaController(RoutineService routineService) {
        this.routineService = routineService;
    }

    // GET /rutinas  -> lista todas
    // GET /rutinas?objetivo=Pérdida de peso&nivel=Principiante -> filtra
    @GetMapping
    public ResponseEntity<List<Rutina>> listar(
            @RequestParam(required = false) String objetivo,
            @RequestParam(required = false) String nivel) {

        if (objetivo == null || objetivo.isBlank()) {
            return ResponseEntity.ok(routineService.listarTodas());
        }
        return ResponseEntity.ok(routineService.filtrarPorObjetivoYNivel(objetivo, nivel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rutina> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(routineService.obtenerPorId(id));
    }
}