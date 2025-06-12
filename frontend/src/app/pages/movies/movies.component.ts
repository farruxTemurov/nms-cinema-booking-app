import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../services/movie.service';
import { TheaterService } from '../../services/theater.service';
import { Movie } from '../../models/movie.model';
import { Theater } from '../../models/theater.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss']
})
export class MoviesComponent implements OnInit {
  movies: Movie[] = [];
  theaters: Theater[] = [];
  errorMessage = '';
  addMovieError = '';
  successMessage = '';
  isAdmin = false;

  // Form model for adding a movie
  newMovie: Movie = {
    title: '',
    language: '',
    genre: '',
    rating: 0,
    theater: undefined
  };

  constructor(
    private movieService: MovieService,
    private theaterService: TheaterService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadMovies();
    this.loadTheaters();
    this.isAdmin = this.authService.isAdmin();
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

  loadTheaters(): void {
    this.theaterService.getAllTheaters().subscribe({
      next: (data) => {
        this.theaters = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load theaters: ' + err.message;
      }
    });
  }

  addMovie(): void {
    this.addMovieError = '';
    this.successMessage = '';

    if (!this.newMovie.theater || !this.newMovie.theater.id) {
      this.addMovieError = 'Please select a valid theater.';
      return;
    }

    // Prepare payload with only the theater ID
    const movieToAdd: Movie = {
      ...this.newMovie,
      theater: { id: this.newMovie.theater.id } as Theater
    };

    // ✅ Let the interceptor handle the token, no manual headers
    this.movieService.addMovie(movieToAdd).subscribe({
      next: (addedMovie) => {
        this.movies.push(addedMovie);
        this.newMovie = { title: '', language: '', genre: '', rating: 0, theater: undefined };
        this.successMessage = 'Movie added successfully!';
      },
      error: (err) => {
        if (err.status === 403) {
          this.addMovieError = 'You are not authorized to add a movie.';
        } else if (err.error && err.error.message) {
          this.addMovieError = 'Failed to add movie: ' + err.error.message;
        } else if (typeof err.error === 'string') {
          this.addMovieError = 'Failed to add movie: ' + err.error;
        } else {
          this.addMovieError = 'Failed to add movie. Please try again.';
        }
      }
    });
  }

  getTheaterName(movie: Movie): string {
    return movie.theater?.name || 'N/A';
  }
}
