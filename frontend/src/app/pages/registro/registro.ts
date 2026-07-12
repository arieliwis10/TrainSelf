import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './registro.html',
  styleUrl: './registro.css'
})
export class RegistroComponent {
  nombre = '';
  email = '';
  password = '';
  error = signal('');
  success = signal('');
  cargando = signal(false);

  constructor(private router: Router, private authService: AuthService) {}

  registrar() {
    if (!this.nombre || !this.email || !this.password) {
      this.error.set('Completa todos los campos');
      return;
    }
    this.error.set('');
    this.cargando.set(true);
    this.authService.registrar(this.nombre, this.email, this.password).subscribe({
      next: () => {
        this.cargando.set(false);
        this.success.set('¡Cuenta creada! Redirigiendo...');
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err) => {
        this.cargando.set(false);
        this.error.set(err.error?.error || 'No se pudo crear la cuenta');
      }
    });
  }
}