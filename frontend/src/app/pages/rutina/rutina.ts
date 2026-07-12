import { Component, OnInit, signal } from '@angular/core';
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
  rutina = signal<Rutina | null>(null);
  cargando = signal(true);
  error = signal('');
  completando = signal(false);
  mensajeExito = signal('');

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private rutinasService: RutinasService,
    private sesionesService: SesionesService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.rutinasService.obtenerPorId(id).subscribe({
      next: (r) => { this.rutina.set(r); this.cargando.set(false); },
      error: () => { this.error.set('No se pudo cargar la rutina'); this.cargando.set(false); }
    });
  }

  marcarCompletada() {
    const rutinaActual = this.rutina();
    if (!rutinaActual) return;

    const usuarioId = Number(localStorage.getItem('usuarioId'));
    if (!usuarioId) {
      this.error.set('Debes iniciar sesión de nuevo');
      return;
    }

    this.completando.set(true);
    this.sesionesService.completarSesion(usuarioId, rutinaActual.id, rutinaActual.duracionEstimadaMin)
      .subscribe({
        next: () => { this.completando.set(false); this.mensajeExito.set('¡Rutina completada! +50 pts'); },
        error: () => { this.completando.set(false); this.error.set('No se pudo registrar la sesión'); }
      });
  }

  volver() {
    this.router.navigate(['/objetivo']);
  }

  verRanking() {
  this.router.navigate(['/leaderboard']);
}
}