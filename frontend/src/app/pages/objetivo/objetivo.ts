import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RutinasService } from '../../services/rutinas.service';

interface ObjetivoOpcion {
  nombre: string;
  icono: string;
  descripcion: string;
}

@Component({
  selector: 'app-objetivo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './objetivo.html',
  styleUrl: './objetivo.css'
})
export class ObjetivoComponent {
  objetivos: ObjetivoOpcion[] = [
    { nombre: 'Pérdida de peso', icono: '🔥', descripcion: 'Cardio + quema calórica' },
    { nombre: 'Ganancia muscular', icono: '💪', descripcion: 'Fuerza + volumen' },
    { nombre: 'Resistencia', icono: '⚡', descripcion: 'Cardio + resistencia aeróbica' }
  ];

  niveles = ['Principiante', 'Intermedio', 'Avanzado'];

  objetivoSeleccionado = signal('');
  nivelSeleccionado = signal('Principiante');
  error = signal('');
  cargando = signal(false);

  constructor(private rutinasService: RutinasService, private router: Router) {}

  seleccionarObjetivo(nombre: string) {
    this.objetivoSeleccionado.set(nombre);
    this.error.set('');
  }

  seleccionarNivel(nivel: string) {
    this.nivelSeleccionado.set(nivel);
  }

  continuar() {
    if (!this.objetivoSeleccionado()) {
      this.error.set('Elige un objetivo para continuar');
      return;
    }

    this.cargando.set(true);
    this.rutinasService.buscarPorObjetivoYNivel(this.objetivoSeleccionado(), this.nivelSeleccionado())
      .subscribe({
        next: (rutinas) => {
          this.cargando.set(false);
          if (rutinas.length === 0) {
            this.error.set('No hay rutinas disponibles para esa combinación todavía');
            return;
          }
          this.router.navigate(['/rutina', rutinas[0].id]);
        },
        error: () => {
          this.cargando.set(false);
          this.error.set('No se pudieron cargar las rutinas');
        }
      });
  }
}