import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface UsuarioAdmin {
  id: number;
  nombre: string;
  correo: string;
  rol: string;
  puntosAcumulados: number;
}

export interface ActividadDia {
  fecha: string;
  cantidad: number;
}

export interface Logro {
  usuarioId: number;
  nombre: string;
  rutinasCompletadas: number;
  medalla: string;
}

@Injectable({ providedIn: 'root' })
export class AdminExtrasService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  usuarios(): Observable<UsuarioAdmin[]> {
    return this.http.get<UsuarioAdmin[]>(`${this.apiUrl}/admin/usuarios`);
  }

  actividad7Dias(): Observable<ActividadDia[]> {
    return this.http.get<ActividadDia[]>(`${this.apiUrl}/admin/insights/actividad-7-dias`);
  }

  logros(): Observable<Logro[]> {
    return this.http.get<Logro[]>(`${this.apiUrl}/admin/insights/logros`);
  }
}