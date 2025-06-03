package com.controller;

import com.dto.JwtAuthenticationResponse;
import com.dto.LoginRequest;
import com.dto.RegisterRequest;
import com.model.Role;
import com.model.User;
import com.repository.UserRepository;
import com.security.JwtTokenProvider;
import com.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserServiceImpl userService;
	private final UserRepository userRepository;
	private final JwtTokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("Email address already in use.");
		}

		User user = User.builder().fullName(request.getFullName()).email(request.getEmail())
				.password(request.getPassword()) // Password will be encoded in service
				.role(Role.CUSTOMER).build();

		userService.registerUser(user);

		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = tokenProvider.generateToken(loginRequest.getEmail());

			return ResponseEntity.ok(new JwtAuthenticationResponse(token));
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new JwtAuthenticationResponse("Invalid credentials"));
		} catch (Exception ex) {
			ex.printStackTrace(); // <--- add this line to see full error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed due to server error");
		}
	}
}
