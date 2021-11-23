package com.finzly.loanapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.finzly.loanapp.dao.UserDao;
import com.finzly.loanapp.model.User;

@Component
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private UserDao userInterface;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userInterface.findByEmail(username);
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException(username + "Not Found");
		}

		UserDetailsImplement userDetailsImplement = new UserDetailsImplement(user);

		return userDetailsImplement;
	}

}
