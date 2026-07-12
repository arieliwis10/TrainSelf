package com.duoc.ms_admin.controller;

import com.duoc.ms_admin.model.Rutina;
import com.duoc.ms_admin.service.AdminRutinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutinas")
public class AdminRutinaController {

    private final AdminRutinaService service;

    public AdminRutinaController(AdminRutinaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Rutina>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Rutina> crear(@RequestBody Rutina rutina) {
        return ResponseEntity.ok(service.crear(rutina));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rutina> actualizar(@PathVariable Long id, @RequestBody Rutina rutina) {
        return ResponseEntity.ok(service.actualizar(id, rutina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}