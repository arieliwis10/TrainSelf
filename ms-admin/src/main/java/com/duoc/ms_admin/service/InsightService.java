package com.duoc.ms_admin.service;

import com.duoc.ms_admin.repository.RutinaRepository;
import com.duoc.ms_admin.repository.SesionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InsightService {

    private final SesionRepository sesionRepository;
    private final RutinaRepository rutinaRepository;

    public InsightService(SesionRepository sesionRepository, RutinaRepository rutinaRepository) {
        this.sesionRepository = sesionRepository;
        this.rutinaRepository = rutinaRepository;
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

    public Map<String, Object> resumen() {
        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
        LocalDateTime hace7Dias = LocalDateTime.now().minusDays(7);

        long sesionesHoy = sesionRepository.contarSesionesDesde(inicioHoy);
        long usuariosActivos = sesionRepository.contarUsuariosActivosDesde(hace7Dias);
        long rutinasTotales = rutinaRepository.count();

        return Map.of(
                "sesionesHoy", sesionesHoy,
                "usuariosActivos", usuariosActivos,
                "rutinasTotales", rutinasTotales
        );
    }

    public List<Map<String, Object>> rutinasPorObjetivo() {
        return rutinaRepository.contarPorObjetivo().stream()
                .map(fila -> Map.<String, Object>of("objetivo", fila[0], "cantidad", fila[1]))
                .collect(Collectors.toList());
    }
}