package com.duoc.ms_leaderboard.service;

import com.duoc.ms_leaderboard.model.Usuario;
import com.duoc.ms_leaderboard.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaderboardServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    private Usuario crearUsuario(Long id, String nombre, Integer puntos) throws Exception {
        Usuario u = new Usuario();
        setField(u, "id", id);
        setField(u, "nombre", nombre);
        setField(u, "puntosAcumulados", puntos);
        return u;
    }

    private void setField(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void rankingTop_asignaPosicionesEnOrden() throws Exception {
        List<Usuario> usuarios = List.of(
                crearUsuario(2L, "Carlos Lopez", 580),
                crearUsuario(3L, "Maria Ruiz", 490),
                crearUsuario(1L, "Ariel Galvez", 320)
        );
        when(usuarioRepository.rankingCompleto()).thenReturn(usuarios);

        List<RankingEntry> ranking = leaderboardService.rankingTop(10);

        assertEquals(3, ranking.size());
        assertEquals(1, ranking.get(0).getPosicion());
        assertEquals("Carlos Lopez", ranking.get(0).getNombre());
        assertEquals(2, ranking.get(1).getPosicion());
        assertEquals(3, ranking.get(2).getPosicion());
    }

    @Test
    void rankingTop_respetaElLimite() throws Exception {
        List<Usuario> usuarios = List.of(
                crearUsuario(1L, "A", 100),
                crearUsuario(2L, "B", 90),
                crearUsuario(3L, "C", 80)
        );
        when(usuarioRepository.rankingCompleto()).thenReturn(usuarios);

        List<RankingEntry> ranking = leaderboardService.rankingTop(2);

        assertEquals(2, ranking.size());
    }

    @Test
    void rankingTop_usuarioSinPuntos_tratadoComoCero() throws Exception {
        List<Usuario> usuarios = List.of(crearUsuario(1L, "Nuevo", null));
        when(usuarioRepository.rankingCompleto()).thenReturn(usuarios);

        List<RankingEntry> ranking = leaderboardService.rankingTop(10);

        assertEquals(0, ranking.get(0).getPuntos());
    }

    @Test
    void posicionDeUsuario_usuarioExistente_retornaSuPosicion() throws Exception {
        List<Usuario> usuarios = List.of(
                crearUsuario(2L, "Carlos Lopez", 580),
                crearUsuario(1L, "Ariel Galvez", 320)
        );
        when(usuarioRepository.rankingCompleto()).thenReturn(usuarios);

        RankingEntry entry = leaderboardService.posicionDeUsuario(1L);

        assertEquals(2, entry.getPosicion());
        assertEquals(320, entry.getPuntos());
    }

    @Test
    void posicionDeUsuario_usuarioInexistente_lanzaExcepcion() throws Exception {
        when(usuarioRepository.rankingCompleto()).thenReturn(List.of(crearUsuario(1L, "Solo", 100)));

        assertThrows(RuntimeException.class, () -> leaderboardService.posicionDeUsuario(999L));
    }
}