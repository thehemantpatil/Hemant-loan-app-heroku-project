package com.finzly.loanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finzly.loanapp.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	User findByEmail(String email);

	boolean existsByEmail(String email);
}
