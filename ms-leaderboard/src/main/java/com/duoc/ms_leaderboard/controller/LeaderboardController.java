package com.duoc.ms_leaderboard.controller;

import com.duoc.ms_leaderboard.service.LeaderboardService;
import com.duoc.ms_leaderboard.service.RankingEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    // GET /leaderboard?top=10
    @GetMapping
    public ResponseEntity<List<RankingEntry>> ranking(
            @RequestParam(defaultValue = "10") int top) {
        return ResponseEntity.ok(leaderboardService.rankingTop(top));
    }

    // GET /leaderboard/usuarios/4
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<RankingEntry> miPosicion(@PathVariable Long id) {
        return ResponseEntity.ok(leaderboardService.posicionDeUsuario(id));
    }
}