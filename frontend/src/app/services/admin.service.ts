import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Resumen {
  sesionesHoy: number;
  rutinasTotales: number;
  usuariosActivos: number;
}

export interface RutinaPorObjetivo {
  objetivo: string;
  cantidad: number;
}

export interface RutinaMasUsada {
  rutinaId: number;
  nombre: string;
  vecesUsada: number;
}

@Injectable({ providedIn: 'root' })
export class AdminService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  resumen(): Observable<Resumen> {
    return this.http.get<Resumen>(`${this.apiUrl}/admin/insights/resumen`);
  }

  rutinasPorObjetivo(): Observable<RutinaPorObjetivo[]> {
    return this.http.get<RutinaPorObjetivo[]>(`${this.apiUrl}/admin/insights/rutinas-por-objetivo`);
  }

  rutinasMasUsadas(): Observable<RutinaMasUsada[]> {
    return this.http.get<RutinaMasUsada[]>(`${this.apiUrl}/admin/insights/rutinas-mas-usadas`);
  }
}