package com.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTheaterId(Long theaterId);
}
