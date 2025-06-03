package com.service.impl;

import com.model.User;
import com.repository.UserRepository; // <--- THIS MUST BE HERE
import org.springframework.security.crypto.password.PasswordEncoder;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder; // add this

	@Override
	public User registerUser(User user) {
		// encode password before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> authenticate(String email, String rawPassword) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			// check raw password against encoded password
			if (passwordEncoder.matches(rawPassword, user.getPassword())) {
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}
}
