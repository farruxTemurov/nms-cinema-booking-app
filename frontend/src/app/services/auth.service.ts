import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {jwtDecode} from 'jwt-decode';  // make sure you import jwt-decode like this
import { JwtAuthenticationResponse } from '../models/jwt-authentication-response';

const BASE_URL = 'http://localhost:9090/api/auth';
const TOKEN_KEY = 'authToken';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) { }

  login(credentials: { email: string; password: string }): Observable<JwtAuthenticationResponse> {
    return this.http.post<JwtAuthenticationResponse>(`${BASE_URL}/login`, credentials);
  }

  register(data: { fullName: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${BASE_URL}/register`, data, { responseType: 'text' });
  }

  saveToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
    this.isLoggedInSubject.next(true);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    this.isLoggedInSubject.next(false);
  }

  isLoggedIn(): Observable<boolean> {
    return this.isLoggedInSubject.asObservable();
  }

  hasToken(): boolean {
    return !!localStorage.getItem(TOKEN_KEY);
  }

  getUserEmail(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded: any = jwtDecode(token);
      return decoded.sub || decoded.username || null;
    } catch {
      return null;
    }
  }

  isAdmin(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const decoded: any = jwtDecode(token);
      const roles = decoded.roles || decoded.authorities || [];
      return roles.includes('ROLE_ADMIN');
    } catch {
      return false;
    }
  }
}
