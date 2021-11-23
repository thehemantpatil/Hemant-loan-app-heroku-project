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
@Table(name = "PaymentDetail")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	private Long loanId;
	private Date repayDate;
	private double interestAmount;
	private long repayAmount;
	private String paymentStatus;

	public Payment() {
	}

	public Payment(Map paymentRecipt) {

		super();

		String repayDateString = paymentRecipt.get("repayDate").toString();
		int year = Integer.parseInt(repayDateString.substring(0, 4));
		int month = Integer.parseInt(repayDateString.substring(5, 7));
		int day = Integer.parseInt(repayDateString.substring(8, 10));

		System.out.println(year + " " + "year");

		this.loanId = Long.parseLong(paymentRecipt.get("loanId").toString());
		this.repayDate = new Date(year - 1900, month, day);
		this.interestAmount = Double.parseDouble(paymentRecipt.get("interestAmount").toString());
		this.repayAmount = Long.parseLong(paymentRecipt.get("repayAmount").toString());
		this.paymentStatus = (String) paymentRecipt.get("paymentStatus");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public long getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(long repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", loanId=" + loanId + ", repayDate=" + repayDate + ", interestAmount="
				+ interestAmount + ", repayAmount=" + repayAmount + ", paymentStatus=" + paymentStatus + "]";
	}
}
