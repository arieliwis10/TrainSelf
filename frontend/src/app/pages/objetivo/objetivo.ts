import { Component } from '@angular/core';
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

  objetivoSeleccionado = '';
  nivelSeleccionado = 'Principiante';
  error = '';
  cargando = false;

  constructor(private rutinasService: RutinasService, private router: Router) {}

  seleccionarObjetivo(nombre: string) {
    this.objetivoSeleccionado = nombre;
    this.error = '';
  }

  seleccionarNivel(nivel: string) {
    this.nivelSeleccionado = nivel;
  }

  continuar() {
    if (!this.objetivoSeleccionado) {
      this.error = 'Elige un objetivo para continuar';
      return;
    }

    this.cargando = true;
    this.rutinasService.buscarPorObjetivoYNivel(this.objetivoSeleccionado, this.nivelSeleccionado)
      .subscribe({
        next: (rutinas) => {
          this.cargando = false;
          if (rutinas.length === 0) {
            this.error = 'No hay rutinas disponibles para esa combinación todavía';
            return;
          }
          this.router.navigate(['/rutina', rutinas[0].id]);
        },
        error: () => {
          this.cargando = false;
          this.error = 'No se pudieron cargar las rutinas';
        }
      });
  }
}