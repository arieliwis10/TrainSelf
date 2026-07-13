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

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'objetivo', component: ObjetivoComponent, canActivate: [authGuard] },
  { path: 'rutina/:id', component: RutinaComponent, canActivate: [authGuard] },
  { path: 'leaderboard', component: LeaderboardComponent, canActivate: [authGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [authGuard, adminGuard] },
  { path: '**', redirectTo: 'login' },
  { path: 'perfil/:id', component: PerfilComponent, canActivate: [authGuard] },

];