import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Usuario {
  id: number;
  nombre: string;
  correo: string;
  rol: string;
  puntosAcumulados: number;
  fechaRegistro: string;
}

export interface Sesion {
  id: number;
  usuarioId: number;
  rutinaId: number;
  fechaInicio: string;
  completada: boolean;
  puntosObtenidos: number;
  duracionRealMin: number;
}

@Injectable({ providedIn: 'root' })
export class UsuariosService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  perfil(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/usuarios/${id}`);
  }

  historial(id: number): Observable<Sesion[]> {
    return this.http.get<Sesion[]>(`${this.apiUrl}/usuarios/${id}/sesiones`);
  }
}