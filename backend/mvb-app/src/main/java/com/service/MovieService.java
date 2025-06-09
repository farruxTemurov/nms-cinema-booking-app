package com.service;

import com.dto.MovieRequest;
import com.model.Movie;
import java.util.List;

public interface MovieService {
    Movie addMovie(MovieRequest movieRequest); 
    List<Movie> getAllMovies();
}
