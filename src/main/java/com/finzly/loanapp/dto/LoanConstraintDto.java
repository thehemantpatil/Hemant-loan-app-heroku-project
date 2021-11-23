package com.finzly.loanapp.dto;

import java.util.ArrayList;
import java.util.List;

public class LoanConstraintDto {

	private double interestRate = 10;
	private List paymentTerm = new ArrayList();
	
	
	
	public LoanConstraintDto() {
		super();
		paymentTerm.add("Evenly");
		paymentTerm.add("Interest");
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public List getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(List paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	@Override
	public String toString() {
		return "LoanConstraint [interestRate=" + interestRate + ", paymentTerm=" + paymentTerm + "]";
	}

}
