import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RutinasService, Rutina } from '../../services/rutinas.service';
import { SesionesService } from '../../services/sesiones.service';

@Component({
  selector: 'app-rutina',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './rutina.html',
  styleUrl: './rutina.css'
})
export class RutinaComponent implements OnInit {
  rutina: Rutina | null = null;
  cargando = true;
  error = '';
  completando = false;
  mensajeExito = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private rutinasService: RutinasService,
    private sesionesService: SesionesService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.rutinasService.obtenerPorId(id).subscribe({
      next: (r) => { this.rutina = r; this.cargando = false; },
      error: () => { this.error = 'No se pudo cargar la rutina'; this.cargando = false; }
    });
  }

  marcarCompletada() {
    if (!this.rutina) return;
    const usuarioId = Number(localStorage.getItem('usuarioId'));
    if (!usuarioId) {
      this.error = 'Debes iniciar sesión de nuevo';
      return;
    }

    this.completando = true;
    this.sesionesService.completarSesion(usuarioId, this.rutina.id, this.rutina.duracionEstimadaMin)
      .subscribe({
        next: () => { this.completando = false; this.mensajeExito = '¡Rutina completada! +50 pts'; },
        error: () => { this.completando = false; this.error = 'No se pudo registrar la sesión'; }
      });
  }

  volver() {
    this.router.navigate(['/objetivo']);
  }
}