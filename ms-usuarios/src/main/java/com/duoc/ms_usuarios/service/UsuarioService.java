package com.duoc.ms_usuarios.service;

import com.duoc.ms_usuarios.model.Sesion;
import com.duoc.ms_usuarios.model.Usuario;
import com.duoc.ms_usuarios.repository.SesionRepository;
import com.duoc.ms_usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private static final int PUNTOS_POR_RUTINA_COMPLETADA = 50;

    private final UsuarioRepository usuarioRepository;
    private final SesionRepository sesionRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, SesionRepository sesionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sesionRepository = sesionRepository;
    }

    public Usuario obtenerPerfil(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));
    }

    public List<Sesion> historialSesiones(Long usuarioId) {
        return sesionRepository.findByUsuarioIdOrderByFechaInicioDesc(usuarioId);
    }

    @Transactional
    public Sesion completarRutina(Long usuarioId, SesionRequest request) {
        Usuario usuario = obtenerPerfil(usuarioId);

        Sesion sesion = new Sesion();
        sesion.setUsuarioId(usuarioId);
        sesion.setRutinaId(request.getRutinaId());
        sesion.setDuracionRealMin(request.getDuracionRealMin());
        sesion.setCompletada(true);
        sesion.setPuntosObtenidos(PUNTOS_POR_RUTINA_COMPLETADA);

        sesionRepository.save(sesion);

        int puntosActuales = usuario.getPuntosAcumulados() != null ? usuario.getPuntosAcumulados() : 0;
        usuario.setPuntosAcumulados(puntosActuales + PUNTOS_POR_RUTINA_COMPLETADA);
        usuarioRepository.save(usuario);

        return sesion;
    }
}