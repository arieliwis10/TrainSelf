import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Objetivo {
  id: number;
  nombre: string;
  descripcion: string;
}

export interface RutinaAdmin {
  id?: number;
  nombre: string;
  objetivo: Objetivo;
  nivel: string;
  duracionEstimadaMin: number;
}

@Injectable({ providedIn: 'root' })
export class AdminRutinasService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  listar(): Observable<RutinaAdmin[]> {
    return this.http.get<RutinaAdmin[]>(`${this.apiUrl}/admin/rutinas`);
  }

  objetivos(): Observable<Objetivo[]> {
    return this.http.get<Objetivo[]>(`${this.apiUrl}/admin/objetivos`);
  }

  crear(rutina: RutinaAdmin): Observable<RutinaAdmin> {
    return this.http.post<RutinaAdmin>(`${this.apiUrl}/admin/rutinas`, rutina);
  }

  actualizar(id: number, rutina: RutinaAdmin): Observable<RutinaAdmin> {
    return this.http.put<RutinaAdmin>(`${this.apiUrl}/admin/rutinas/${id}`, rutina);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/admin/rutinas/${id}`);
  }
}