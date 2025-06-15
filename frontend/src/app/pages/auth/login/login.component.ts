import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { JwtAuthenticationResponse } from 'src/app/models/jwt-authentication-response'; // adjust path

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';
  token = '';

  constructor(private authService: AuthService) {}

  login() {
    console.log('Login attempt:', { email: this.email, password: this.password ? '***' : '' });

    const credentials = { email: this.email, password: this.password };

    this.authService.login(credentials).subscribe({
      next: (res: JwtAuthenticationResponse) => {
        console.log('Login response received:', res);

        if (res.accessToken) {
          this.token = res.accessToken;
          this.authService.saveToken(res.accessToken);
          this.errorMessage = '';
          console.log('Login successful. Token saved:', res.accessToken);
        } else if ((res as any).message) {
          this.errorMessage = (res as any).message;
          this.token = '';
          console.warn('Login failed with message:', this.errorMessage);
        } else {
          this.errorMessage = 'Unknown response format';
          this.token = '';
          console.warn('Login response had no accessToken or message:', res);
        }
      },
      error: (err) => {
        console.error('Login error object:', err);
        this.errorMessage = err.error?.message || 'Login failed. Please try again.';
        this.token = '';
        console.error('Login error message:', this.errorMessage);
      }
    });
  }
}
