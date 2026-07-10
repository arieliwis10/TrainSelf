import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

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

  constructor(private router: Router) {}

  login() {
    if (!this.email || !this.password) {
      this.error = 'Completa todos los campos';
      return;
    }
    // Por ahora redirige directo, luego conectamos con JWT
    this.router.navigate(['/registro']);
  }
}