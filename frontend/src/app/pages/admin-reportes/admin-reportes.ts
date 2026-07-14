import { AfterViewInit, Component, ElementRef, OnInit, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Chart, registerables } from 'chart.js';
import { AdminExtrasService, ActividadDia } from '../../services/admin-extras.service';

Chart.register(...registerables);

@Component({
  selector: 'app-admin-reportes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-reportes.html',
  styleUrl: './admin-reportes.css'
})
export class AdminReportesComponent implements OnInit {
  @ViewChild('lineCanvas') lineCanvas!: ElementRef<HTMLCanvasElement>;

  actividad = signal<ActividadDia[]>([]);
  cargando = signal(true);
  error = signal('');

  constructor(private adminExtrasService: AdminExtrasService, private router: Router) {}

  ngOnInit() {
    this.adminExtrasService.actividad7Dias().subscribe({
      next: (data) => {
        this.actividad.set(data);
        this.cargando.set(false);
        setTimeout(() => this.dibujarLinea(data), 0);
      },
      error: () => { this.error.set('No se pudo cargar la actividad'); this.cargando.set(false); }
    });
  }

  private dibujarLinea(data: ActividadDia[]) {
    if (!this.lineCanvas) return;

    new Chart(this.lineCanvas.nativeElement, {
      type: 'line',
      data: {
        labels: data.map(d => d.fecha.slice(5)),
        datasets: [{
          label: 'Sesiones',
          data: data.map(d => d.cantidad),
          borderColor: '#5b3df5',
          backgroundColor: 'rgba(91, 61, 245, 0.15)',
          fill: true,
          tension: 0.3
        }]
      },
      options: {
        plugins: { legend: { display: false } },
        scales: {
          x: { ticks: { color: '#999' }, grid: { color: '#2a2a33' } },
          y: { ticks: { color: '#999' }, grid: { color: '#2a2a33' }, beginAtZero: true }
        }
      }
    });
  }

  totalSemana(): number {
    return this.actividad().reduce((sum, d) => sum + d.cantidad, 0);
  }

  irADashboard() { this.router.navigate(['/admin']); }
  irARutinas() { this.router.navigate(['/admin/rutinas']); }
  irAUsuarios() { this.router.navigate(['/admin/usuarios']); }
  irALogros() { this.router.navigate(['/admin/logros']); }
}