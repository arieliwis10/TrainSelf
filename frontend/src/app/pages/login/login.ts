import { Component, signal } from '@angular/core';
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
  error = signal('');
  cargando = signal(false);

  constructor(private router: Router, private authService: AuthService) {}

  login() {
    if (!this.email || !this.password) {
      this.error.set('Completa todos los campos');
      return;
    }
    this.error.set('');
    this.cargando.set(true);
    this.authService.login(this.email, this.password).subscribe({
      next: () => {
        this.cargando.set(false);
        this.router.navigate(['/objetivo']);
      },
      error: (err) => {
        this.cargando.set(false);
        this.error.set(err.error?.error || 'Correo o contraseña incorrectos');
      }
    });
  }
}