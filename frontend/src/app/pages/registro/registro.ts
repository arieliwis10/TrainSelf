import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

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

  constructor(private router: Router) {}

  registrar() {
    if (!this.nombre || !this.email || !this.password) {
      this.error = 'Completa todos los campos';
      return;
    }
    this.success = '¡Cuenta creada! Redirigiendo...';
    setTimeout(() => this.router.navigate(['/login']), 2000);
  }
}