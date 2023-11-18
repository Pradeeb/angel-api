package com.angel.api.practice.service;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.LoginRequest;

public interface ILoginService {

	public ApiResponse login(LoginRequest  login);
	public ApiResponse userProfile();
	public ApiResponse getToken(String token);
	
}
