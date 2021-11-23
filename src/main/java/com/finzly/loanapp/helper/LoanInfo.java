package com.finzly.loanapp.helper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoanInfo {
	private long loanId;

	private Map<String, Integer> paymentFreq = new HashMap<String, Integer>(
			Map.of("1 Month", 1, "3 Month", 3, "6 Month", 6, "12 Month", 12));
	private Double loanTenure;
	private int totalMonths;
	private double interestRate;
	private int paymentFrequency;
	private int totalFullPaymentCycles;
	private int halfPaymentCycle;
	private long principalAmount;
	private long monthlyEmiAmount;
	private double monthlyInterest;
	private String paymentTerm;
	private int paymentCycleCount;
	private int totalPaymentCycles;
	private long totalAmount;
	private Date maturiTyDate;

	public LoanInfo(Map loan) {
		super();
		this.loanTenure = Double.parseDouble(loan.get("loanTenure").toString());
		this.totalMonths = (int) (loanTenure * 12);
		this.interestRate = Double.parseDouble(loan.get("interestRate").toString());
		this.paymentFrequency = paymentFreq.get(loan.get("paymentFrequency").toString());
		this.totalFullPaymentCycles = totalMonths / paymentFrequency;
		this.halfPaymentCycle = (totalMonths % paymentFrequency != 0) ? 1 : 0;
		this.principalAmount = Long.parseLong(loan.get("loanAmount").toString());
		this.monthlyEmiAmount = principalAmount / totalMonths;
		this.monthlyInterest = interestRate / 12 / 100;
		this.paymentTerm = loan.get("paymentTerm").toString();
		this.paymentCycleCount = 0;
		this.totalPaymentCycles = this.halfPaymentCycle + this.totalFullPaymentCycles;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getMaturiTyDate() {
		return maturiTyDate;
	}

	public void setMaturiTyDate(Date maturiTyDate) {
		this.maturiTyDate = maturiTyDate;
	}

	public int getTotalPaymentCycles() {
		return totalPaymentCycles;
	}

	public void setTotalPaymentCycles(int totalPaymentCycles) {
		this.totalPaymentCycles = totalPaymentCycles;
	}

	public void incrementPaymentCycleCount() {
		this.paymentCycleCount += 1;
	}

	public Map<String, Integer> getPaymentFreq() {
		return paymentFreq;
	}

	public void setPaymentFreq(Map<String, Integer> paymentFreq) {
		this.paymentFreq = paymentFreq;
	}

	public Double getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(Double loanTenure) {
		this.loanTenure = loanTenure;
	}

	public int getPaymentCycleCount() {
		return paymentCycleCount;
	}

	public void setPaymentCycleCount(int paymentCycleCount) {
		this.paymentCycleCount = paymentCycleCount;
	}

	public int getTotalMonths() {
		return totalMonths;
	}

	public void setTotalMonths(int totalMonths) {
		this.totalMonths = totalMonths;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(int paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public int getTotalFullPaymentCycles() {
		return this.totalFullPaymentCycles;
	}

	public void setTotalFullPaymentCycles(int totalPaymentCycles) {
		this.totalFullPaymentCycles = totalPaymentCycles;
	}

	public int getHalfPaymentCycle() {
		return halfPaymentCycle;
	}

	public void setHalfPaymentCycle(int halfPaymentCycle) {
		this.halfPaymentCycle = halfPaymentCycle;
	}

	public long getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(long repayAmount) {
		this.principalAmount -= repayAmount;
	}

	public long getMonthlyEmiAmount() {
		return monthlyEmiAmount;
	}

	public void setMonthlyEmiAmount(long monthlyEmiAmount) {
		this.monthlyEmiAmount = monthlyEmiAmount;
	}

	public double getMonthlyInterest() {
		return monthlyInterest;
	}

	public void setMonthlyInterest(double monthlyInterest) {
		this.monthlyInterest = monthlyInterest;
	}

	public long getloanId() {
		return this.loanId;
	}

	public void setloanId(long loanId) {
		this.loanId = loanId;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

}
