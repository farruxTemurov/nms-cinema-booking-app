import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:9090/api/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${BASE_URL}/login`, credentials);
  }

  register(data: { fullName: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${BASE_URL}/register`, data);
  }
}
