import { AfterViewInit, Component, ElementRef, OnInit, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Chart, registerables } from 'chart.js';
import { AdminService, Resumen, RutinaPorObjetivo, RutinaMasUsada } from '../../services/admin.service';

Chart.register(...registerables);

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.html',
  styleUrl: './admin.css'
})
export class AdminComponent implements OnInit, AfterViewInit {
  @ViewChild('donutCanvas') donutCanvas!: ElementRef<HTMLCanvasElement>;

  resumen = signal<Resumen | null>(null);
  rutinasPorObjetivo = signal<RutinaPorObjetivo[]>([]);
  rutinasMasUsadas = signal<RutinaMasUsada[]>([]);
  cargando = signal(true);
  error = signal('');

  private colores = ['#5b3df5', '#22c55e', '#f97316', '#94a3b8'];

  constructor(private adminService: AdminService, private router: Router) {}

  ngOnInit() {
    this.adminService.resumen().subscribe({
      next: (r) => this.resumen.set(r),
      error: () => this.error.set('No se pudo cargar el resumen')
    });

    this.adminService.rutinasMasUsadas().subscribe({
      next: (r) => { this.rutinasMasUsadas.set(r); this.cargando.set(false); },
      error: () => { this.cargando.set(false); }
    });

    this.adminService.rutinasPorObjetivo().subscribe({
      next: (data) => {
        this.rutinasPorObjetivo.set(data);
        setTimeout(() => this.dibujarDona(data), 0);
      }
    });
  }

  ngAfterViewInit() {}

  private dibujarDona(data: RutinaPorObjetivo[]) {
    if (!this.donutCanvas) return;

    new Chart(this.donutCanvas.nativeElement, {
      type: 'doughnut',
      data: {
        labels: data.map(d => d.objetivo),
        datasets: [{
          data: data.map(d => d.cantidad),
          backgroundColor: this.colores,
          borderWidth: 0
        }]
      },
      options: {
        cutout: '65%',
        plugins: {
          legend: { display: false }
        }
      }
    });
  }

  color(index: number): string {
    return this.colores[index % this.colores.length];
  }

  porcentaje(cantidad: number): number {
    const total = this.rutinasPorObjetivo().reduce((sum, r) => sum + r.cantidad, 0);
    return total > 0 ? Math.round((cantidad / total) * 100) : 0;
  }

  totalRutinas(): number {
    return this.rutinasPorObjetivo().reduce((sum, r) => sum + r.cantidad, 0);
  }

  volver() {
    this.router.navigate(['/objetivo']);
  }

  irARutinas() {
    this.router.navigate(['/admin/rutinas']);
  }

  irAUsuarios() {
    this.router.navigate(['/admin/usuarios']);
  }

  irAReportes() {
    this.router.navigate(['/admin/reportes']);
  }

  irALogros() {
    this.router.navigate(['/admin/logros']);
  }
}