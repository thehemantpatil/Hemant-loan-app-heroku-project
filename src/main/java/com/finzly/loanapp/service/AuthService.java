package com.finzly.loanapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finzly.loanapp.config.UserDetailsImplement;
import com.finzly.loanapp.config.UserDetailsServiceImplementation;
import com.finzly.loanapp.dao.UserDao;
import com.finzly.loanapp.dto.AuthDto;
import com.finzly.loanapp.model.User;
import com.finzly.loanapp.util.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsServiceImplementation userDetailsServiceImplementation;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	public boolean createUser(User user) {
		try {
			String password = user.getPassword();
			String bcryptedPassword = passwordEncoder.encode(password);

			user.setPassword(bcryptedPassword);
			userDao.save(user);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean signUp(User user) {
		if (user != null) {
			String email = user.getEmail();
			boolean isUserExists = userDao.existsByEmail(email);

			if (!isUserExists) {
				boolean isuserRegistered = createUser(user);
				return isuserRegistered;
			}
		}

		return false;
	}

	public String generateToken(User userObject) {
		String email = userObject.getEmail();
		User user = null;
		String customerName = userObject.getCustomerName();

		UserDetailsImplement userDetails = new UserDetailsImplement(userObject);

		if (customerName == null) {
			user = userDao.findByEmail(email);
		} else {
			user = userObject;
		}

		String token = jwtUtil.generateToken(userDetails, user);
		return token;
	}

	public AuthDto generateAuthResponse(int statusCode) {
		AuthDto authDto = new AuthDto();
		authDto.setStatusCode(statusCode);
		return authDto;
	}

	public AuthDto generateAuthResponse(int statusCode, String token) {
		AuthDto authDto = new AuthDto();
		authDto.setStatusCode(statusCode);
		authDto.setToken(token);
		return authDto;
	}

	public void authenticateUser(User user) {
		String email = user.getEmail();
		String password = user.getPassword();

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}
}
