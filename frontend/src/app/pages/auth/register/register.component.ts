import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

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

  constructor(private http: HttpClient, private router: Router, private toastr: ToastrService) { }

  register() {
    const payload = {
      fullName: this.fullName,
      email: this.email,
      password: this.password
    };

    this.http.post<any>('http://localhost:9090/api/auth/register', payload).subscribe({
      next: (response) => {
        this.toastr.success(response.message, 'Registration Successful');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.successMessage = '';
        this.toastr.error(err.error.message || 'Something went wrong. Please try again.', 'Registration Failed');
      }
    });

  }
}


