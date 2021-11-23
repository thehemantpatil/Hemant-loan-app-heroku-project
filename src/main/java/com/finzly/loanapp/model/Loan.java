package com.finzly.loanapp.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LoanDetail")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	private Long customerId;
	private String loanReason;
	private Date tradeDate;
	private Date maturityDate;
	private String paymentFrequency;
	private String paymentTerm;
	private double interestRate;
	private double loanAmount;
	private double totalAmount;

	public Loan() {

	}

	public Loan(Map loan) {

		super();
		try {
			this.tradeDate = new Date();
			this.customerId = Long.parseLong(String.valueOf(loan.get("customerId")));
			this.loanReason = (String) loan.get("loanReason");
			this.paymentFrequency = String.valueOf(loan.get("paymentFrequency"));
			this.paymentTerm = String.valueOf(loan.get("paymentTerm"));
			this.interestRate = Double.parseDouble(String.valueOf(loan.get("interestRate")));
			this.loanAmount = Double.parseDouble(String.valueOf(loan.get("loanAmount")));
//		this.totalAmount = Double.parseDouble(String.valueOf(loan.get("totalAmount")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getLoanReason() {
		return loanReason;
	}

	public void setLoanReason(String loanReason) {
		this.loanReason = loanReason;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", customerId=" + customerId + ", loanReason=" + loanReason + ", tradeDate="
				+ tradeDate + ", maturityDate=" + maturityDate + ", paymentFrequency=" + paymentFrequency
				+ ", paymentTerm=" + paymentTerm + ", interestRate=" + interestRate + ", loanAmount=" + loanAmount
				+ ", totalAmount=" + totalAmount + "]";
	}

}
