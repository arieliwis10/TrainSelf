import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AdminExtrasService, Logro } from '../../services/admin-extras.service';

@Component({
  selector: 'app-admin-logros',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-logros.html',
  styleUrl: './admin-logros.css'
})
export class AdminLogrosComponent implements OnInit {
  logros = signal<Logro[]>([]);
  cargando = signal(true);
  error = signal('');

  constructor(private adminExtrasService: AdminExtrasService, private router: Router) {}

  ngOnInit() {
    this.adminExtrasService.logros().subscribe({
      next: (data) => { this.logros.set(data); this.cargando.set(false); },
      error: () => { this.error.set('No se pudieron cargar los logros'); this.cargando.set(false); }
    });
  }

  irADashboard() { this.router.navigate(['/admin']); }
  irARutinas() { this.router.navigate(['/admin/rutinas']); }
  irAUsuarios() { this.router.navigate(['/admin/usuarios']); }
  irAReportes() { this.router.navigate(['/admin/reportes']); }
}