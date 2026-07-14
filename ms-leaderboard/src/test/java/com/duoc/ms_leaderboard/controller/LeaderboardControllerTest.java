package com.duoc.ms_leaderboard.controller;

import com.duoc.ms_leaderboard.service.LeaderboardService;
import com.duoc.ms_leaderboard.service.RankingEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaderboardControllerTest {

    @Mock
    private LeaderboardService leaderboardService;

    @InjectMocks
    private LeaderboardController leaderboardController;

    @Test
    void ranking_conTopPorDefecto_delegaAlServicioCon10() {
        List<RankingEntry> lista = List.of(new RankingEntry(1, 2L, "Carlos Lopez", 580));
        when(leaderboardService.rankingTop(10)).thenReturn(lista);

        ResponseEntity<List<RankingEntry>> respuesta = leaderboardController.ranking(10);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        verify(leaderboardService).rankingTop(10);
    }

    @Test
    void ranking_conTopPersonalizado_delegaConEseValor() {
        when(leaderboardService.rankingTop(5)).thenReturn(List.of());

        leaderboardController.ranking(5);

        verify(leaderboardService).rankingTop(5);
        verify(leaderboardService, never()).rankingTop(10);
    }

    @Test
    void miPosicion_delegaAlServicioYRetorna200() {
        RankingEntry entry = new RankingEntry(4, 4L, "Ariel Test2", 50);
        when(leaderboardService.posicionDeUsuario(4L)).thenReturn(entry);

        ResponseEntity<RankingEntry> respuesta = leaderboardController.miPosicion(4L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(4, respuesta.getBody().getPosicion());
        assertEquals(50, respuesta.getBody().getPuntos());
    }
}