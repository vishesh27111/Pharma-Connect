import { CanActivateFn } from '@angular/router';

export const userAuthGuard: CanActivateFn = (route, state) => {
  if (localStorage.getItem('token') &&
    localStorage.getItem('emailId') &&
    localStorage.getItem('userId')) {
      return true;
  }
  return false;
};


export const storeAuthGuard: CanActivateFn = (route, state) => {
  if (localStorage.getItem('token') &&
    localStorage.getItem('storeId')) {
      return true;
  }
  return false;
};