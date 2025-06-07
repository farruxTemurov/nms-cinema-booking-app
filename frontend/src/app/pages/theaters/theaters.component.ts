import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface Theater {
  id?: number;
  name: string;
  location: string;
}

@Component({
  selector: 'app-theater',
  templateUrl: './theaters.component.html',
  styleUrls: ['./theaters.component.scss']
})
export class TheatersComponent implements OnInit {
  theaters: Theater[] = [];
  newTheater: Theater = { name: '', location: '' };
  successMessage = '';
  errorMessage = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchTheaters();
  }

  fetchTheaters() {
    this.http.get<Theater[]>('http://localhost:9090/api/theaters').subscribe({
      next: (data) => {
        this.theaters = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load theaters.';
      }
    });
  }

  addTheater() {
    const token = localStorage.getItem('token'); // Adjust if you store token differently
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.post<Theater>('http://localhost:9090/api/theaters', this.newTheater, { headers }).subscribe({
      next: (response) => {
        this.successMessage = 'Theater added successfully!';
        this.errorMessage = '';
        this.newTheater = { name: '', location: '' };
        this.fetchTheaters(); // Refresh list
      },
      error: (err) => {
        this.successMessage = '';
        if (err.status === 403) {
          this.errorMessage = 'You are not authorized to add a theater.';
        } else {
          this.errorMessage = 'Failed to add theater.';
        }
      }
    });
  }
}
