import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface AuthResponse {
  token: string;
  id: number;
  nombre: string;
  correo: string;
  rol: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  login(correo: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/login`, { correo, password })
      .pipe(tap(res => this.guardarSesion(res)));
  }

  registrar(nombre: string, correo: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/register`, { nombre, correo, password })
      .pipe(tap(res => this.guardarSesion(res)));
  }

  private guardarSesion(res: AuthResponse) {
    localStorage.setItem('token', res.token);
    localStorage.setItem('usuarioId', String(res.id));
    localStorage.setItem('nombre', res.nombre);
    localStorage.setItem('rol', res.rol);
  }

  logout() {
    localStorage.clear();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  estaAutenticado(): boolean {
    return !!this.getToken();
  }
}