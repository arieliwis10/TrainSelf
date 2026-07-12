package com.duoc.ms_admin.service;

import com.duoc.ms_admin.repository.SesionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InsightService {

    private final SesionRepository sesionRepository;

    public InsightService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    public List<Map<String, Object>> rutinasMasUsadas() {
        return sesionRepository.rutinasMasUsadas().stream()
                .map(fila -> Map.of(
                        "rutinaId", fila[0],
                        "nombre", fila[1],
                        "vecesUsada", fila[2]
                ))
                .collect(Collectors.toList());
    }
}