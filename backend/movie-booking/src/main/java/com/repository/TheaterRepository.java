package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
