import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface RankingEntry {
  posicion: number;
  usuarioId: number;
  nombre: string;
  puntos: number;
}

@Injectable({ providedIn: 'root' })
export class LeaderboardService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  ranking(top: number = 10): Observable<RankingEntry[]> {
    return this.http.get<RankingEntry[]>(`${this.apiUrl}/leaderboard?top=${top}`);
  }

  miPosicion(usuarioId: number): Observable<RankingEntry> {
    return this.http.get<RankingEntry>(`${this.apiUrl}/leaderboard/usuarios/${usuarioId}`);
  }
}