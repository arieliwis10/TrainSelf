package com.duoc.ms_admin.controller;

import com.duoc.ms_admin.model.Objetivo;
import com.duoc.ms_admin.repository.ObjetivoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/objetivos")
public class ObjetivoController {

    private final ObjetivoRepository objetivoRepository;

    public ObjetivoController(ObjetivoRepository objetivoRepository) {
        this.objetivoRepository = objetivoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Objetivo>> listar() {
        return ResponseEntity.ok(objetivoRepository.findAll());
    }
}