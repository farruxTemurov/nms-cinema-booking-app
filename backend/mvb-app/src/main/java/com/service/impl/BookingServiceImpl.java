package com.service.impl;

import com.dto.BookingRequest;
import com.model.Booking;
import com.model.Movie;
import com.model.Theater;
import com.model.User;
import com.repository.BookingRepository;
import com.repository.MovieRepository;
import com.repository.TheaterRepository;
import com.repository.UserRepository;
import com.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TheaterRepository theaterRepository;

	@Override
	public Booking bookMovie(BookingRequest request, String userEmail) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

		Movie movie = movieRepository.findById(request.getMovieId())
				.orElseThrow(() -> new RuntimeException("Movie not found"));

		Theater theater = theaterRepository.findById(request.getTheaterId())
				.orElseThrow(() -> new RuntimeException("Theater not found"));

		Booking booking = Booking.builder().bookingTime(request.getBookingTime()).user(user).movie(movie)
				.theater(theater).build();

		return bookingRepository.save(booking);
	}

	@Override
	public List<Booking> getBookingsByUserEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		return bookingRepository.findByUserId(user.getId());
	}

	@Override
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}
}
