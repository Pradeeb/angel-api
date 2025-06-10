package com.angel.api.practice.model;

public class LoginRequest {  //  LoginRequest
	
	private String clientcode;
	private String password;
	private String totp;
	
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
	public String getTotp() {
		return totp;
	}
	public void setTotp(String totp) {
		this.totp = totp;
	}
	
	public LoginRequest(String clientcode, String password, String totp) {
		super();
		this.clientcode = clientcode;
		this.password = password;
		this.totp = totp;
	}

}
