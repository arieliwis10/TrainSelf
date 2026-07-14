import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegistroComponent } from './pages/registro/registro';
import { ObjetivoComponent } from './pages/objetivo/objetivo';
import { RutinaComponent } from './pages/rutina/rutina';
import { LeaderboardComponent } from './pages/leaderboard/leaderboard';
import { authGuard } from './guards/auth.guard';
import { AdminComponent } from './pages/admin/admin';
import { adminGuard } from './guards/admin.guard';
import { PerfilComponent } from './pages/perfil/perfil';
import { AdminRutinasComponent } from './pages/admin-rutinas/admin-rutinas';
import { AdminUsuariosComponent } from './pages/admin-usuarios/admin-usuarios';
import { AdminReportesComponent } from './pages/admin-reportes/admin-reportes';
import { AdminLogrosComponent } from './pages/admin-logros/admin-logros';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'objetivo', component: ObjetivoComponent, canActivate: [authGuard] },
  { path: 'rutina/:id', component: RutinaComponent, canActivate: [authGuard] },
  { path: 'leaderboard', component: LeaderboardComponent, canActivate: [authGuard] },
  { path: 'perfil/:id', component: PerfilComponent, canActivate: [authGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [authGuard, adminGuard] },
  { path: 'admin/rutinas', component: AdminRutinasComponent, canActivate: [authGuard, adminGuard] },
  { path: 'admin/usuarios', component: AdminUsuariosComponent, canActivate: [authGuard, adminGuard] },
  { path: 'admin/reportes', component: AdminReportesComponent, canActivate: [authGuard, adminGuard] },
  { path: 'admin/logros', component: AdminLogrosComponent, canActivate: [authGuard, adminGuard] },
  { path: '**', redirectTo: 'login' }
];