import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { UserService } from '../services/user.service';
import { Observable } from 'rxjs';
 
@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private authService: UserService) {}
 
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    // Check if the user is logged in
    if (this.authService.isLoggedIn()) {
      const currentUser = this.authService.getCurrentUser();
      
      if (!currentUser) {
        this.router.navigate(['/login']);
        return false;
      }
      
      // Check if route requires specific role(s)
      const requiredRole = route.data['role'];
      const requiredRoles = route.data['roles'];
      
      if (requiredRole) {
        // Single role requirement
        if (currentUser.role === requiredRole) {
          return true; // Allow access for users with matching role
        } else {
          // Redirect to appropriate dashboard based on user's role
          this.redirectToDashboard(currentUser.role);
          return false;
        }
      } else if (requiredRoles) {
        // Multiple roles requirement
        if (requiredRoles.includes(currentUser.role)) {
          return true; // Allow access if user's role is in the required roles array
        } else {
          // Redirect to appropriate dashboard based on user's role
          this.redirectToDashboard(currentUser.role);
          return false;
        }
      }
      
      // For routes that don't require a specific role, just being logged in is enough
      return true;
    }
   
    // If not logged in, redirect to login page with the return url
    this.router.navigate(['/login'], {
      queryParams: { returnUrl: state.url }
    });
   
    return false;
  }
  
  private redirectToDashboard(role: string): void {
    switch (role) {
      case 'admin':
        this.router.navigate(['/admin-dashboard']);
        break;
      case 'Support':
        this.router.navigate(['/support-dashboard']);
        break;
      case 'Client':
        this.router.navigate(['/my-bookings']);
        break;
      default:
        this.router.navigate(['/']);
        break;
    }
  }
}