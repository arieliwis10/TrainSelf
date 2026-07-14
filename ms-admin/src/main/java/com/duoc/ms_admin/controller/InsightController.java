package com.duoc.ms_admin.controller;

import com.duoc.ms_admin.service.InsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/insights")
public class InsightController {

    private final InsightService insightService;

    public InsightController(InsightService insightService) {
        this.insightService = insightService;
    }

    @GetMapping("/rutinas-mas-usadas")
    public ResponseEntity<List<Map<String, Object>>> rutinasMasUsadas() {
        return ResponseEntity.ok(insightService.rutinasMasUsadas());
    }

    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumen() {
        return ResponseEntity.ok(insightService.resumen());
    }

    @GetMapping("/rutinas-por-objetivo")
    public ResponseEntity<List<Map<String, Object>>> rutinasPorObjetivo() {
        return ResponseEntity.ok(insightService.rutinasPorObjetivo());
    }

    @GetMapping("/actividad-7-dias")
    public ResponseEntity<List<Map<String, Object>>> actividad7Dias() {
        return ResponseEntity.ok(insightService.actividad7Dias());
    }

    @GetMapping("/logros")
    public ResponseEntity<List<Map<String, Object>>> logros() {
        return ResponseEntity.ok(insightService.logros());
    }
    
}