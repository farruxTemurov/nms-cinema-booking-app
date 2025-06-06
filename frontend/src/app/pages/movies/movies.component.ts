import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss']
})
export class MoviesComponent implements OnInit {

  movies: Movie[] = [];
  errorMessage = '';
  addMovieError = '';
  isAdmin = false;

  newMovie: Movie = {
    title: '',
    language: '',
    genre: '',
    rating: 0,
    theater: null
  };

  constructor(
    private movieService: MovieService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadMovies();
    this.isAdmin = this.authService.isAdmin(); // ← Real role check from token
  }

  loadMovies(): void {
    this.movieService.getAllMovies().subscribe({
      next: (data) => {
        this.movies = data;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = 'Failed to load movies: ' + err.message;
      }
    });
  }

  addMovie(): void {
    this.addMovieError = '';

    this.movieService.addMovie(this.newMovie).subscribe({
      next: (movie) => {
        this.movies.push(movie);
        this.newMovie = { title: '', language: '', genre: '', rating: 0, theater: null };
      },
      error: (err) => {
        this.addMovieError = 'Failed to add movie: ' + err.message;
      }
    });
  }
}
