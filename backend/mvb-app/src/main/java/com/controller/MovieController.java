package com.controller;

import com.dto.MovieRequest;
import com.model.Movie;
import com.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Endpoint to add a new movie (e.g., by Admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Movie addMovie(@RequestBody MovieRequest movieRequest) {
        return movieService.addMovie(movieRequest);
    }


    // Endpoint to get all movies (for listing/showing)
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
}
