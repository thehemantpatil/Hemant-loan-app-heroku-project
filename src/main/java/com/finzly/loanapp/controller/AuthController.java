package com.finzly.loanapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finzly.loanapp.config.UserDetailsServiceImplementation;
import com.finzly.loanapp.dao.UserDao;
import com.finzly.loanapp.dto.AuthDto;
import com.finzly.loanapp.model.User;
import com.finzly.loanapp.service.AuthService;
import com.finzly.loanapp.util.JwtUtil;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Registered"),
			@ApiResponse(code = 204, message = "User Already Exists"),
			@ApiResponse(code = 403, message = "forbidden") })
	public ResponseEntity<AuthDto> signup(@RequestBody User user) {
		try {

			boolean isUserRegistered = authService.signUp(user);
			logger.info(isUserRegistered ? "User is successfully registered" : "User is already exists");

			if (isUserRegistered) {
				AuthDto authResponse = authService.generateAuthResponse(200);
				authResponse.setStatusMessage("Successfully Registered");
				return ResponseEntity.ok(authResponse);
			}

			return ResponseEntity.status(204).build();

		} catch (Exception e) {
			return ResponseEntity.status(403).build();
		}
	}

	@PostMapping("login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Logged In"),
			@ApiResponse(code = 204, message = "Invalid Credentials"),
			@ApiResponse(code = 403, message = "forbidden") })
	public ResponseEntity<AuthDto> login(@RequestBody User user) {
		try {
			// if user is not authenticated will throw exception
			authService.authenticateUser(user);

			logger.info("User is authenticated");

			String token = authService.generateToken(user);
			AuthDto authResponse = authService.generateAuthResponse(200, token);
			authResponse.setStatusMessage("Successfully Logged In");
			return ResponseEntity.ok(authResponse);

		} catch (BadCredentialsException badCredentialsException) {
			logger.info("User is not authenticated");
			AuthDto authResponse = authService.generateAuthResponse(200, "");
			authResponse.setStatusMessage("Invalid Credentials");
			return ResponseEntity.ok(authResponse);

		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.status(204).build();
		}
	}
}
