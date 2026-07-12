import { Component } from '@angular/core';
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
  error = '';
  success = '';
  cargando = false;

  constructor(private router: Router, private authService: AuthService) {}

  registrar() {
    if (!this.nombre || !this.email || !this.password) {
      this.error = 'Completa todos los campos';
      return;
    }

    this.error = '';
    this.cargando = true;

    this.authService.registrar(this.nombre, this.email, this.password).subscribe({
      next: () => {
        this.cargando = false;
        this.success = '¡Cuenta creada! Redirigiendo...';
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err) => {
        this.cargando = false;
        this.error = err.error?.error || 'No se pudo crear la cuenta';
      }
    });
  }
}