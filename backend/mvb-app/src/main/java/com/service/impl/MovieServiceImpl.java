package com.service.impl;

import com.dto.MovieRequest;
import com.model.Movie;
import com.model.Theater;
import com.repository.MovieRepository;
import com.repository.TheaterRepository;
import com.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TheaterRepository theaterRepository;
	
	@Override
	public Movie addMovie(MovieRequest movieRequest) {
	    Theater theater = theaterRepository.findById(movieRequest.getTheaterId())
	        .orElseThrow(() -> new RuntimeException("Theater not found"));

	    Movie movie = Movie.builder()
	        .title(movieRequest.getTitle())
	        .language(movieRequest.getLanguage())
	        .genre(movieRequest.getGenre())
	        .rating(movieRequest.getRating())
	        .theater(theater)
	        .build();

	    return movieRepository.save(movie);
	}

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
}
