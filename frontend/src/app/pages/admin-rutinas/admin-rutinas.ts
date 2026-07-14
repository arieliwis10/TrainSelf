import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminRutinasService, RutinaAdmin, Objetivo } from '../../services/admin-rutinas.service';

@Component({
  selector: 'app-admin-rutinas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-rutinas.html',
  styleUrl: './admin-rutinas.css'
})
export class AdminRutinasComponent implements OnInit {
  rutinas = signal<RutinaAdmin[]>([]);
  objetivos = signal<Objetivo[]>([]);
  cargando = signal(true);
  error = signal('');
  exito = signal('');

  mostrarModal = signal(false);
  editando = signal(false);
  rutinaEnEdicion: RutinaAdmin = this.rutinaVacia();

  rutinaAEliminar = signal<RutinaAdmin | null>(null);

  niveles = ['Principiante', 'Intermedio', 'Avanzado'];

  constructor(private adminRutinasService: AdminRutinasService, private router: Router) {}

  ngOnInit() {
    this.cargarDatos();
  }

  private rutinaVacia(): RutinaAdmin {
    return { nombre: '', objetivo: { id: 0, nombre: '', descripcion: '' }, nivel: 'Principiante', duracionEstimadaMin: 10 };
  }

  cargarDatos() {
    this.cargando.set(true);
    this.adminRutinasService.listar().subscribe({
      next: (data) => { this.rutinas.set(data); this.cargando.set(false); },
      error: () => { this.error.set('No se pudieron cargar las rutinas'); this.cargando.set(false); }
    });

    this.adminRutinasService.objetivos().subscribe({
      next: (data) => this.objetivos.set(data)
    });
  }

  abrirCrear() {
    this.rutinaEnEdicion = this.rutinaVacia();
    if (this.objetivos().length > 0) {
      const primero = this.objetivos()[0];
      this.rutinaEnEdicion.objetivo = { id: primero.id, nombre: primero.nombre, descripcion: primero.descripcion };
    }
    this.editando.set(false);
    this.mostrarModal.set(true);
    this.error.set('');
  }

  abrirEditar(r: RutinaAdmin) {
    this.rutinaEnEdicion = {
      id: r.id,
      nombre: r.nombre,
      objetivo: { id: r.objetivo.id, nombre: r.objetivo.nombre, descripcion: r.objetivo.descripcion },
      nivel: r.nivel,
      duracionEstimadaMin: r.duracionEstimadaMin
    };
    this.editando.set(true);
    this.mostrarModal.set(true);
    this.error.set('');
  }

  cerrarModal() {
    this.mostrarModal.set(false);
  }

  guardar() {
    if (!this.rutinaEnEdicion.nombre || !this.rutinaEnEdicion.objetivo.id) {
      this.error.set('Completa nombre y objetivo');
      return;
    }

    const obs = this.editando()
      ? this.adminRutinasService.actualizar(this.rutinaEnEdicion.id!, this.rutinaEnEdicion)
      : this.adminRutinasService.crear(this.rutinaEnEdicion);

    obs.subscribe({
      next: () => {
        this.mostrarModal.set(false);
        this.exito.set(this.editando() ? 'Rutina actualizada' : 'Rutina creada');
        this.cargarDatos();
        setTimeout(() => this.exito.set(''), 2500);
      },
      error: () => this.error.set('No se pudo guardar la rutina')
    });
  }

  confirmarEliminar(r: RutinaAdmin) {
    this.rutinaAEliminar.set(r);
  }

  cancelarEliminar() {
    this.rutinaAEliminar.set(null);
  }

  eliminar() {
    const r = this.rutinaAEliminar();
    if (!r || !r.id) return;

    this.adminRutinasService.eliminar(r.id).subscribe({
      next: () => {
        this.rutinaAEliminar.set(null);
        this.exito.set('Rutina eliminada');
        this.cargarDatos();
        setTimeout(() => this.exito.set(''), 2500);
      },
      error: () => {
        this.error.set('No se pudo eliminar la rutina');
        this.rutinaAEliminar.set(null);
      }
    });
  }

  volver() {
    this.router.navigate(['/admin']);
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