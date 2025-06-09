//src/app/core/services/user.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
 
@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = 'https://srxgoioj5k.execute-api.ap-south-1.amazonaws.com/dev';
  private usersApiUrl = `${this.baseUrl}/auth/sign-up`;
  private loginApiUrl = `${this.baseUrl}/auth/sign-in`;
 
  constructor(private http: HttpClient) {}
 
  // For registration
  registerUser(userData: any): Observable<any> {
    return this.http.post<any>(this.usersApiUrl, userData);
  }
 
  // For login
  loginUser(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(this.loginApiUrl, credentials);
  }
 
  // Get all users (if supported by the backend)
  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.usersApiUrl);
  }
 
  // Get a user by email
  getUserByEmail(email: string): Observable<any> {
    return this.http.get<any[]>(`${this.usersApiUrl}?email=${email}`).pipe(
      map(users => users[0] || null)
    );
  }
 
  // Get current user
  getCurrentUser(): any {
    return JSON.parse(localStorage.getItem('currentUser') || '{}');
  }
 
  // Get user role
  getUserRole(): string | null {
    return this.getCurrentUser()?.role || 'user';
  }
 
  // Get user ID
  getUserId(): string | null {
    return this.getCurrentUser()?.id || null;
  }
 
  // Check if user is logged in
  isLoggedIn(): boolean {
    return !!localStorage.getItem('currentUser');
  }
}