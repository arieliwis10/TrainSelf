package com.duoc.ms_leaderboard.service;

import com.duoc.ms_leaderboard.model.Usuario;
import com.duoc.ms_leaderboard.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderboardService {

    private final UsuarioRepository usuarioRepository;

    public LeaderboardService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<RankingEntry> rankingTop(int limite) {
        List<Usuario> usuarios = usuarioRepository.rankingCompleto();
        List<RankingEntry> ranking = new ArrayList<>();

        int posicion = 1;
        for (Usuario u : usuarios) {
            if (posicion > limite) break;
            int puntos = u.getPuntosAcumulados() != null ? u.getPuntosAcumulados() : 0;
            ranking.add(new RankingEntry(posicion, u.getId(), u.getNombre(), puntos));
            posicion++;
        }
        return ranking;
    }

    public RankingEntry posicionDeUsuario(Long usuarioId) {
        List<Usuario> usuarios = usuarioRepository.rankingCompleto();

        int posicion = 1;
        for (Usuario u : usuarios) {
            if (u.getId().equals(usuarioId)) {
                int puntos = u.getPuntosAcumulados() != null ? u.getPuntosAcumulados() : 0;
                return new RankingEntry(posicion, u.getId(), u.getNombre(), puntos);
            }
            posicion++;
        }
        throw new RuntimeException("Usuario no encontrado en el ranking: " + usuarioId);
    }
}