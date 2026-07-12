import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegistroComponent } from './pages/registro/registro';
import { ObjetivoComponent } from './pages/objetivo/objetivo';
import { RutinaComponent } from './pages/rutina/rutina';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'objetivo', component: ObjetivoComponent, canActivate: [authGuard] },
  { path: 'rutina/:id', component: RutinaComponent, canActivate: [authGuard] }
];