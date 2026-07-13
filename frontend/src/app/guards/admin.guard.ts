import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const adminGuard: CanActivateFn = () => {
  const router = inject(Router);
  const rol = localStorage.getItem('rol');

  if (rol === 'ADMIN') {
    return true;
  }

  router.navigate(['/objetivo']);
  return false;
};