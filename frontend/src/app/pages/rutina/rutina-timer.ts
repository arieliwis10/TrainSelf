import { Component, OnDestroy, OnInit, signal } from '@angular/core';
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
export class RutinaComponent implements OnInit, OnDestroy {
  rutina = signal<Rutina | null>(null);
  cargando = signal(true);
  error = signal('');
  completando = signal(false);
  mensajeExito = signal('');

  ejercicioActual = signal(0);
  tiempoRestante = signal(0);
  pausado = signal(true);

  private intervalo: ReturnType<typeof setInterval> | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private rutinasService: RutinasService,
    private sesionesService: SesionesService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.rutinasService.obtenerPorId(id).subscribe({
      next: (r) => {
        this.rutina.set(r);
        this.cargando.set(false);
        if (r.ejercicios.length > 0) {
          this.tiempoRestante.set(r.ejercicios[0].duracionSeg);
        }
      },
      error: () => { this.error.set('No se pudo cargar la rutina'); this.cargando.set(false); }
    });
  }

  ngOnDestroy() {
    this.detenerIntervalo();
  }

  private detenerIntervalo() {
    if (this.intervalo) {
      clearInterval(this.intervalo);
      this.intervalo = null;
    }
  }

  iniciarIntervalo() {
    this.detenerIntervalo();
    this.intervalo = setInterval(() => {
      const restante = this.tiempoRestante();
      if (restante <= 1) {
        this.tiempoRestante.set(0);
        this.detenerIntervalo();
        this.pausado.set(true);
      } else {
        this.tiempoRestante.set(restante - 1);
      }
    }, 1000);
  }

  togglePausa() {
    if (this.pausado()) {
      this.pausado.set(false);
      this.iniciarIntervalo();
    } else {
      this.pausado.set(true);
      this.detenerIntervalo();
    }
  }

  irAEjercicio(indice: number) {
    const r = this.rutina();
    if (!r || indice < 0 || indice >= r.ejercicios.length) return;

    this.detenerIntervalo();
    this.pausado.set(true);
    this.ejercicioActual.set(indice);
    this.tiempoRestante.set(r.ejercicios[indice].duracionSeg);
  }

  siguiente() {
    this.irAEjercicio(this.ejercicioActual() + 1);
  }

  anterior() {
    this.irAEjercicio(this.ejercicioActual() - 1);
  }

  formatoTiempo(): string {
    const t = this.tiempoRestante();
    const min = Math.floor(t / 60).toString().padStart(2, '0');
    const seg = (t % 60).toString().padStart(2, '0');
    return `${min}:${seg}`;
  }

  progresoPorcentaje(): number {
    const r = this.rutina();
    if (!r || r.ejercicios.length === 0) return 0;
    return Math.round(((this.ejercicioActual() + 1) / r.ejercicios.length) * 100);
  }

  marcarCompletada() {
    const rutinaActual = this.rutina();
    if (!rutinaActual) return;

    const usuarioId = Number(localStorage.getItem('usuarioId'));
    if (!usuarioId) {
      this.error.set('Debes iniciar sesión de nuevo');
      return;
    }

    this.detenerIntervalo();
    this.completando.set(true);
    this.sesionesService.completarSesion(usuarioId, rutinaActual.id, rutinaActual.duracionEstimadaMin)
      .subscribe({
        next: () => { this.completando.set(false); this.mensajeExito.set('¡Rutina completada! +50 pts'); },
        error: () => { this.completando.set(false); this.error.set('No se pudo registrar la sesión'); }
      });
  }

  volver() {
    this.detenerIntervalo();
    this.router.navigate(['/objetivo']);
  }

  verRanking() {
    this.detenerIntervalo();
    this.router.navigate(['/leaderboard']);
  }
}