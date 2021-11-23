package com.finzly.loanapp.dto;

import java.util.List;
import java.util.Map;

public class LoanDto {

	private int statusCode;
	private String statusMessage;
	private List<Map> loanObject;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<Map> getLoanObject() {
		return loanObject;
	}

	public void setLoanObject(List<Map> loanObject) {
		this.loanObject = loanObject;
	}

}
