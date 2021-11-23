package com.finzly.loanapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finzly.loanapp.controller.AuthController;
import com.finzly.loanapp.controller.LoanController;
import com.finzly.loanapp.dao.LoanDao;
import com.finzly.loanapp.dao.PaymentDao;
import com.finzly.loanapp.dto.LoanDto;
import com.finzly.loanapp.helper.LoanInfo;
import com.finzly.loanapp.model.Loan;
import com.finzly.loanapp.model.Payment;

@Service
public class LoanService {

	private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private PaymentDao paymentDao;

//	public boolean loadPaymentCycles(Map<String, Object> loan, Loan loanDetails) {
//		List paymentSchedule = (List) loan.get("paymentCycles");
//		
//		for (int i = 0; i < paymentSchedule.size(); i++) {
//			Map paymentReciept = (Map) paymentSchedule.get(i);
//			paymentReciept.put("loanId", loanDetails.getId());
//			Payment payment = new Payment(paymentReciept);
//			paymentDao.save(payment);
//		}
//		return true;
//	}

//	public boolean createLoan(Map<String, Object> loan) {
//		Map loanList = (Map) loan.get("loanDetails");
//		Loan loanDetails = new Loan(loanList);
//		loanDao.save(loanDetails);
//		loadPaymentCycles(loan, loanDetails);
//		return true;
//	}

	public void generatePaymentCycle(LoanInfo loanInfo) {
		try {
			String PaymentTerm = loanInfo.getPaymentTerm();
			loadPaymentCycles(loanInfo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void loadHalfPaymentCycles(LoanInfo loanInfo) {
		int paymentCycle = 1;
		String paymentCycleType = "HalfPaymentCycle";
		Payment payment = new Payment();

		loanInfo.incrementPaymentCycleCount();

		setLoanId(loanInfo, payment);
		setRepayAmount(loanInfo, payment, paymentCycleType);
		setInterestAmount(loanInfo, payment, paymentCycleType);
		setPaymentStatus(payment);
		setRepayDate(loanInfo, payment, paymentCycle, paymentCycleType);

		paymentDao.save(payment);
		logger.info(payment.toString());
	}

	private void setRepayAmount(LoanInfo loanInfo, Payment payment, String paymentCycleType) {
		long repayAmount = 0;
		long totalAmount = loanInfo.getTotalAmount();
		String paymentTerm = loanInfo.getPaymentTerm();

		if (paymentTerm.equals("Evenly")) {
			repayAmount = (long) (loanInfo.getMonthlyEmiAmount() * loanInfo.getPaymentFrequency());
		}

		if (paymentTerm.equals("Interest") && loanInfo.getPaymentCycleCount() == loanInfo.getTotalPaymentCycles()) {
			repayAmount = loanInfo.getPrincipalAmount();
		}

		if (paymentCycleType.equals("HalfPaymentCycle") && !paymentTerm.equals("Interest")) {
			repayAmount /= 2;
		}

		payment.setRepayAmount(repayAmount);
		logger.info(totalAmount + "repayAmount");
		loanInfo.setTotalAmount(totalAmount + repayAmount);
	}

	private void setInterestAmount(LoanInfo loanInfo, Payment payment, String paymentCycleType) {
		long totalAmount = loanInfo.getTotalAmount();
		long interestAmount = Math.round(
				loanInfo.getPrincipalAmount() * (loanInfo.getMonthlyInterest() * loanInfo.getPaymentFrequency()));

		if (paymentCycleType.equals("HalfPaymentCycle")) {
			interestAmount /= 2;
		}

		payment.setInterestAmount(interestAmount);

		if (loanInfo.getPaymentTerm().equals("Evenly")) {
			loanInfo.setPrincipalAmount(payment.getRepayAmount());
		}
		logger.info(totalAmount + "interest");

		loanInfo.setTotalAmount(interestAmount + totalAmount);

	}

	private void setPaymentStatus(Payment payment) {
		payment.setPaymentStatus("PROJECTED");
	}

	private void setRepayDate(LoanInfo loanInfo, Payment payment, int paymentCycle, String paymentCycleType) {
		int paymentFrequency = loanInfo.getPaymentFrequency();
		Date repayDate = new Date();
		double todaysDate = repayDate.getDate();
		double paymentCycleDate = 30.42 * paymentFrequency * paymentCycle;

		if (paymentCycleType.equals("HalfPaymentCycle")) {
			todaysDate = (30.42 * (paymentFrequency / 2));
		}

		repayDate.setDate((int) (todaysDate + paymentCycleDate));
		payment.setRepayDate(repayDate);

		if (loanInfo.getPaymentCycleCount() == loanInfo.getTotalPaymentCycles()) {
			loanInfo.setMaturiTyDate(repayDate);
		}
	}

	private void setLoanId(LoanInfo loanInfo, Payment payment) {
		logger.info("Loan id is " + loanInfo.getloanId());
		payment.setLoanId(loanInfo.getloanId());
	}

	private void incrementPaymentCycleCount(LoanInfo loanInfo) {
		loanInfo.incrementPaymentCycleCount();
	}

	private void loadPaymentCycles(LoanInfo loanInfo) {
		try {
			String paymentCycleType = "FullPaymentCycle";
			for (int paymentCycle = 1; paymentCycle <= loanInfo.getTotalFullPaymentCycles(); paymentCycle++) {
				Payment payment = new Payment();

				incrementPaymentCycleCount(loanInfo);

				setLoanId(loanInfo, payment);
				setRepayAmount(loanInfo, payment, paymentCycleType);
				setInterestAmount(loanInfo, payment, paymentCycleType);
				setPaymentStatus(payment);
				setRepayDate(loanInfo, payment, paymentCycle, paymentCycleType);

				paymentDao.save(payment);
				logger.info(payment.toString());
			}

			if (loanInfo.getHalfPaymentCycle() == 1) {
				loadHalfPaymentCycles(loanInfo);
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	private void setTotalAmount(long loanId, LoanInfo loanInfo) {
		Loan currentLoan = loanDao.findById(loanId).orElse(new Loan());

		if (currentLoan.getId() != null) {
			currentLoan.setTotalAmount(loanInfo.getTotalAmount());
			currentLoan.setMaturityDate(loanInfo.getMaturiTyDate());
			loanDao.save(currentLoan);
		}
	}

	public boolean createLoan(Map<String, Object> loan) {
		Map loanDetail = (Map) loan.get("loanDetails");
		Loan loanObject = new Loan(loanDetail);
		LoanInfo loanInfo = new LoanInfo(loanDetail);
		loanDao.save(loanObject);
		long loanId = loanObject.getId();
		loanInfo.setloanId(loanId);
		generatePaymentCycle(loanInfo);
		setTotalAmount(loanId, loanInfo);
		logger.info("Loan is created and payment cycles are also generated");
		return true;
	}

	public List<Map> fetchLoanDetails(Map<String, Long> customer) {

		List<Map> loanObject = new ArrayList<Map>();
		ObjectMapper mapper = new ObjectMapper();
		Long customerId = Long.parseLong(String.valueOf(customer.get("customerId")));

		List<Loan> loan = loanDao.findByCustomerId(customerId);

		if (loan == null) {
			return null;
		}

		for (int loanIndex = 0; loanIndex < loan.size(); loanIndex++) {
			Map<String, Object> jsonObject = mapper.convertValue(loan.get(loanIndex), Map.class);
			List<Payment> paymentList = paymentDao.findByLoanId((Long) jsonObject.get("id"));

			for (Payment payment : paymentList) {
				if (jsonObject.get("paymentCycles") == null) {
					jsonObject.put("paymentCycles", new ArrayList());
					ArrayList paymentCycle = (ArrayList) jsonObject.get("paymentCycles");
					paymentCycle.add(payment);
				} else {
					ArrayList paymentCycle = (ArrayList) jsonObject.get("paymentCycles");
					paymentCycle.add(payment);
				}
			}

			loanObject.add(jsonObject);

		}

		return loanObject;
	}

	public LoanDto generateLoanResponse(int statusCode, List<Map> loanObject) {
		LoanDto loanDto = new LoanDto();
		loanDto.setStatusCode(statusCode);
		loanDto.setLoanObject(loanObject);
		return loanDto;

	}

}
