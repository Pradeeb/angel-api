package com.angel.api.practice.service;

import com.angel.api.practice.errorhandle.ApiResponse;

public interface IStockDetails {
	
	public ApiResponse getAllHolgingStokDetails(String token);
	
	public ApiResponse getHolgingStokDetails(String token);

}
