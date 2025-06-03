package com.controller;

import com.dto.BookingRequest;
import com.model.Booking;
import com.service.BookingService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	// ✅ Book movie using authenticated user's identity
	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Booking> bookMovie(@Valid @RequestBody BookingRequest request, Principal principal) {
		Booking booking = bookingService.bookMovie(request, principal.getName());
		return ResponseEntity.ok(booking);
	}

	// ✅ Get current user's bookings
	@GetMapping("/my")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<List<Booking>> getUserBookings(Principal principal) {
		return ResponseEntity.ok(bookingService.getBookingsByUserEmail(principal.getName()));
	}

	// ✅ Admin-only: Get all bookings
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Booking>> getAllBookings() {
		return ResponseEntity.ok(bookingService.getAllBookings());
	}
}
