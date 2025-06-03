package com.service;

import com.dto.BookingRequest;
import com.model.Booking;
import java.util.List;

public interface BookingService {
	Booking bookMovie(BookingRequest request, String userEmail);

	List<Booking> getBookingsByUserEmail(String userEmail);

	List<Booking> getAllBookings(); // for admin
}
