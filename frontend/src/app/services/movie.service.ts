import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movie } from '../models/movie.model';

const BASE_URL = 'http://localhost:9090/api/movies';

@Injectable({
  providedIn: 'root'
})
export class MovieService { // ✅ Correct service name

  constructor(private http: HttpClient) { }

  getAllMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(BASE_URL);
  }

  addMovie(movie: Movie): Observable<Movie> {
    return this.http.post<Movie>(BASE_URL, movie);
  }
}
