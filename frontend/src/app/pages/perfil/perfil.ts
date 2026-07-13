import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuariosService, Usuario, Sesion } from '../../services/usuarios.service';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css'
})
export class PerfilComponent implements OnInit {
  usuario = signal<Usuario | null>(null);
  historial = signal<Sesion[]>([]);
  cargando = signal(true);
  error = signal('');

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuariosService: UsuariosService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.usuariosService.perfil(id).subscribe({
      next: (u) => { this.usuario.set(u); this.cargando.set(false); },
      error: () => { this.error.set('No se pudo cargar el perfil'); this.cargando.set(false); }
    });

    this.usuariosService.historial(id).subscribe({
      next: (s) => this.historial.set(s),
      error: () => {}
    });
  }

  sesionesCompletadas(): number {
    return this.historial().filter(s => s.completada).length;
  }

  volver() {
    this.router.navigate(['/leaderboard']);
  }
}