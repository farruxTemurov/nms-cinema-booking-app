import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  fullName = '';
  email = '';
  password = '';
  successMessage = '';
  errorMessage = '';

  constructor(private http: HttpClient) { }

  register() {
    const payload = {
      fullName: this.fullName,
      email: this.email,
      password: this.password
    };

    this.http.post<any>('http://localhost:9090/api/auth/register', payload).subscribe({
      next: (response) => {
        this.successMessage = response.message;
        this.errorMessage = '';
      },
      error: (err) => {
        this.successMessage = '';
        this.errorMessage = err.error.message || 'Something went wrong. Please try again.';
      }
    });

  }
}


