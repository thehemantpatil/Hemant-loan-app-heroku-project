package com.finzly.loanapp.dto;

public class AuthDto {

	private int statusCode;
	private String statusMessage;
	private String token;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setStatusMessage(String message) {
		this.statusMessage = message;

	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
