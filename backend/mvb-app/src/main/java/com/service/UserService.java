package com.service;

import com.model.User;
import java.util.Optional;
import java.util.List;

public interface UserService {
	User registerUser(User user);

	Optional<User> findByEmail(String email);
	
	Optional<User> authenticate(String email, String password);


	List<User> getAllUsers(); // for admin access if needed
}
