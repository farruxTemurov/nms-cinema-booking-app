package com.service.impl;

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

//	@Override
//	public Movie addMovie(Movie movie) {
//		return movieRepository.save(movie);
//	}
	@Autowired
	private TheaterRepository theaterRepository;

	@Override
	public Movie addMovie(Movie movie) {
	    Long theaterId = movie.getTheater().getId();
	    Theater theater = theaterRepository.findById(theaterId)
	            .orElseThrow(() -> new RuntimeException("Theater not found with ID: " + theaterId));
	    
	    movie.setTheater(theater); // attach full entity
	    return movieRepository.save(movie);
	}


	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
}
