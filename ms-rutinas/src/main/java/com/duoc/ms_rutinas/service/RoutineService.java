package com.duoc.ms_rutinas.service;

import com.duoc.ms_rutinas.model.Rutina;
import com.duoc.ms_rutinas.repository.RutinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RutinaRepository rutinaRepository;

    public RoutineService(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    public List<Rutina> filtrarPorObjetivoYNivel(String objetivo, String nivel) {
        if (nivel == null || nivel.isBlank()) {
            return rutinaRepository.findByObjetivo_Nombre(objetivo);
        }
        return rutinaRepository.findByObjetivo_NombreAndNivel(objetivo, nivel);
    }

    public List<Rutina> listarTodas() {
        return rutinaRepository.findAll();
    }

    public Rutina obtenerPorId(Long id) {
        return rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada: " + id));
    }
}