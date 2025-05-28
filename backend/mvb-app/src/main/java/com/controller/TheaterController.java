package com.controller;

import com.model.Theater;
import com.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

	@Autowired
	private TheaterService theaterService;

	// Add a new theater (e.g., by Admin)
	@PostMapping
	public Theater addTheater(@RequestBody Theater theater) {
		return theaterService.addTheater(theater);
	}

	// Retrieve all theaters
	@GetMapping
	public List<Theater> getAllTheaters() {
		return theaterService.getAllTheaters();
	}
}
