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

    this.http.post('http://localhost:9090/api/auth/register', payload, { responseType: 'text' }).subscribe({
      next: (response) => {
        this.successMessage = response;
        this.errorMessage = '';
        this.fullName = '';
        this.email = '';
        this.password = '';
      },
      error: (err) => {
        this.successMessage = '';
        this.errorMessage = err.error || 'Something went wrong. Please try again.';
      }
    });
  }
}


