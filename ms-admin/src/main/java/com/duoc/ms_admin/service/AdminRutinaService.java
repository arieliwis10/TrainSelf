package com.duoc.ms_admin.service;

import com.duoc.ms_admin.model.Rutina;
import com.duoc.ms_admin.repository.RutinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRutinaService {

    private final RutinaRepository rutinaRepository;

    public AdminRutinaService(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    public List<Rutina> listarTodas() {
        return rutinaRepository.findAll();
    }

    public Rutina crear(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    public Rutina actualizar(Long id, Rutina datos) {
        Rutina existente = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada: " + id));
        existente.setNombre(datos.getNombre());
        existente.setObjetivo(datos.getObjetivo());
        existente.setNivel(datos.getNivel());
        existente.setDuracionEstimadaMin(datos.getDuracionEstimadaMin());
        return rutinaRepository.save(existente);
    }

    public void eliminar(Long id) {
        rutinaRepository.deleteById(id);
    }
}