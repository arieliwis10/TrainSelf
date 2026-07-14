package com.duoc.ms_admin.service;

import com.duoc.ms_admin.model.Usuario;
import com.duoc.ms_admin.repository.RutinaRepository;
import com.duoc.ms_admin.repository.SesionRepository;
import com.duoc.ms_admin.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;

    public InsightService(SesionRepository sesionRepository,
                           RutinaRepository rutinaRepository,
                           UsuarioRepository usuarioRepository) {
        this.sesionRepository = sesionRepository;
        this.rutinaRepository = rutinaRepository;
        this.usuarioRepository = usuarioRepository;
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

    public List<Map<String, Object>> actividad7Dias() {
        LocalDateTime desde = LocalDate.now().minusDays(6).atStartOfDay();
        List<Object[]> filas = sesionRepository.actividadPorDia(desde);

        return filas.stream()
            .map(f -> Map.<String, Object>of("fecha", f[0].toString(), "cantidad", f[1]))
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> logros() {
        List<Object[]> conteos = sesionRepository.sesionesCompletadasPorUsuario();
        List<Usuario> usuarios = usuarioRepository.findAll();

        Map<Long, Long> conteoPorUsuario = conteos.stream()
            .collect(Collectors.toMap(f -> (Long) f[0], f -> (Long) f[1]));

        return usuarios.stream()
            .map(u -> {
                long completadas = conteoPorUsuario.getOrDefault(u.getId(), 0L);
                String medalla = completadas >= 10 ? "🥇 Oro" : completadas >= 5 ? "🥈 Plata" : completadas >= 1 ? "🥉 Bronce" : "Sin logros";
                return Map.<String, Object>of(
                    "usuarioId", u.getId(),
                    "nombre", u.getNombre(),
                    "rutinasCompletadas", completadas,
                    "medalla", medalla
                );
            })
            .sorted((a, b) -> Long.compare((Long) b.get("rutinasCompletadas"), (Long) a.get("rutinasCompletadas")))
            .collect(Collectors.toList());
    }
}