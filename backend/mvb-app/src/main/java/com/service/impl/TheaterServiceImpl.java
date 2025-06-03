package com.service.impl;

import com.model.Theater;
import com.repository.TheaterRepository;
import com.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {

	@Autowired
	private TheaterRepository theaterRepository;

	@Override
	public Theater addTheater(Theater theater) {
		return theaterRepository.save(theater);
	}

	@Override
	public List<Theater> getAllTheaters() {
		return theaterRepository.findAll();
	}
}
