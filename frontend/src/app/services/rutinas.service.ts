import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Objetivo {
  id: number;
  nombre: string;
  descripcion: string;
}

export interface Ejercicio {
  id: number;
  nombre: string;
  descripcion: string | null;
  duracionSeg: number;
  descansoSeg: number;
  orden: number;
  urlAnimacion: string | null;
}

export interface Rutina {
  id: number;
  nombre: string;
  objetivo: Objetivo;
  nivel: string;
  duracionEstimadaMin: number;
  fechaCreacion: string;
  ejercicios: Ejercicio[];
}

@Injectable({ providedIn: 'root' })
export class RutinasService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  buscarPorObjetivoYNivel(objetivo: string, nivel: string): Observable<Rutina[]> {
    const params = new URLSearchParams({ objetivo, nivel });
    return this.http.get<Rutina[]>(`${this.apiUrl}/rutinas?${params.toString()}`);
  }

  obtenerPorId(id: number): Observable<Rutina> {
    return this.http.get<Rutina>(`${this.apiUrl}/rutinas/${id}`);
  }
}