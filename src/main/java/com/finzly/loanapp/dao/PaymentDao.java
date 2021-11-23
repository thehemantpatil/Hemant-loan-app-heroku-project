package com.finzly.loanapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.finzly.loanapp.model.Payment;

@RepositoryRestResource
public interface PaymentDao extends JpaRepository<Payment, Long> {

	List<Payment> findByLoanId(Long id);

}
