import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  cargando = false;

  constructor(private router: Router, private authService: AuthService) {}

  login() {
    if (!this.email || !this.password) {
      this.error = 'Completa todos los campos';
      return;
    }

    this.error = '';
    this.cargando = true;

    this.authService.login(this.email, this.password).subscribe({
      next: () => {
        this.cargando = false;
        this.router.navigate(['/objetivo']); // ajusta a tu ruta principal cuando la tengas
      },
      error: (err) => {
        this.cargando = false;
        this.error = err.error?.error || 'Correo o contraseña incorrectos';
      }
    });
  }
}