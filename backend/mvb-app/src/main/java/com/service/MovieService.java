package com.service;

import com.model.Movie;
import java.util.List;

public interface MovieService {
	Movie addMovie(Movie movie);

	List<Movie> getAllMovies();
}
