package com.angel.api.practice.model;

public class LoginRequest {  //  LoginRequest
	
	private String clientcode;
	private String password;
	private int totp;
	
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTotp() {
		return totp;
	}
	public void setTotp(int totp) {
		this.totp = totp;
	}
	
	public LoginRequest(String clientcode, String password, int totp) {
		super();
		this.clientcode = clientcode;
		this.password = password;
		this.totp = totp;
	}

}
