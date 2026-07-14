import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AdminExtrasService, UsuarioAdmin } from '../../services/admin-extras.service';

@Component({
  selector: 'app-admin-usuarios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-usuarios.html',
  styleUrl: './admin-usuarios.css'
})
export class AdminUsuariosComponent implements OnInit {
  usuarios = signal<UsuarioAdmin[]>([]);
  cargando = signal(true);
  error = signal('');

  constructor(private adminExtrasService: AdminExtrasService, private router: Router) {}

  ngOnInit() {
    this.adminExtrasService.usuarios().subscribe({
      next: (data) => { this.usuarios.set(data); this.cargando.set(false); },
      error: () => { this.error.set('No se pudieron cargar los usuarios'); this.cargando.set(false); }
    });
  }

  irADashboard() { this.router.navigate(['/admin']); }
  irARutinas() { this.router.navigate(['/admin/rutinas']); }
  irAReportes() { this.router.navigate(['/admin/reportes']); }
  irALogros() { this.router.navigate(['/admin/logros']); }
}