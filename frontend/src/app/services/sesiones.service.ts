import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SesionesService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  completarSesion(usuarioId: number, rutinaId: number, duracionRealMin: number): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/usuarios/${usuarioId}/sesiones`,
      { rutinaId, duracionRealMin }
    );
  }
}