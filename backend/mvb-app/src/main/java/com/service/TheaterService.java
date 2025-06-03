package com.service;

import com.model.Theater;
import java.util.List;

public interface TheaterService {
	Theater addTheater(Theater theater);

	List<Theater> getAllTheaters();
}
