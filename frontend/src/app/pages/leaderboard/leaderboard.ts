import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LeaderboardService, RankingEntry } from '../../services/leaderboard.service';

@Component({
  selector: 'app-leaderboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './leaderboard.html',
  styleUrl: './leaderboard.css'
})
export class LeaderboardComponent implements OnInit {
  ranking = signal<RankingEntry[]>([]);
  miPosicion = signal<RankingEntry | null>(null);
  cargando = signal(true);
  error = signal('');

  constructor(private leaderboardService: LeaderboardService, private router: Router) {}

  ngOnInit() {
    const usuarioId = Number(localStorage.getItem('usuarioId'));

    this.leaderboardService.ranking(10).subscribe({
      next: (data) => {
        this.ranking.set(data);
        this.cargando.set(false);

        const yo = data.find(r => r.usuarioId === usuarioId);
        if (yo) {
          this.miPosicion.set(yo);
        } else if (usuarioId) {
          this.leaderboardService.miPosicion(usuarioId).subscribe({
            next: (r) => this.miPosicion.set(r)
          });
        }
      },
      error: () => {
        this.error.set('No se pudo cargar el ranking');
        this.cargando.set(false);
      }
    });
  }

  diferenciaConTercero(): number {
    const lista = this.ranking();
    const yo = this.miPosicion();
    if (!yo || lista.length < 3) return 0;
    const tercero = lista[2];
    return Math.max(0, tercero.puntos - yo.puntos);
  }

  medalla(posicion: number): string {
    if (posicion === 1) return '🥇';
    if (posicion === 2) return '🥈';
    if (posicion === 3) return '🥉';
    return '';
  }

  volver() {
    this.router.navigate(['/objetivo']);
  }
}