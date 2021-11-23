package com.finzly.loanapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.finzly.loanapp.model.Loan;

@RepositoryRestResource
public interface LoanDao extends JpaRepository<Loan, Long> {

	List<Loan> findByCustomerId(Long customerId);

}
