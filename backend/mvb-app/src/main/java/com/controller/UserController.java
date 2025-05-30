package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public User registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}

	@PostMapping("/signin")
	public String loginUser(@RequestBody User user) {
		Optional<User> existingUser = userService.findByEmail(user.getEmail());
		if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
			return "Login successful!";
		} else {
			return "Invalid email or password";
		}
	}

	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
}
