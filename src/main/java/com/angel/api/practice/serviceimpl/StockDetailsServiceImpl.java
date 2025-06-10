package com.angel.api.practice.serviceimpl;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.service.IStockDetails;
import com.angelbroking.smartapi.SmartConnect;
import com.angelbroking.smartapi.http.exceptions.SmartAPIException;

@Service
public class StockDetailsServiceImpl implements IStockDetails {
	
    @Value("${api.key}")
    private String apiKey;

	@Override
	public ApiResponse getAllHolgingStokDetails(String token) {
        SmartConnect connect=new SmartConnect();
        connect.setApiKey(apiKey);
        connect.setAccessToken(token);
        try {
			JSONObject stockDetails = connect.getAllHolding();
			Map<String, Object> dataMap = stockDetails.toMap();
			return new ApiResponse(true, false, dataMap);
		} catch (IOException e) {
			e.printStackTrace();
			return new ApiResponse(false, true, e.getMessage());
		} catch (SmartAPIException e) {
			e.printStackTrace();
			return new ApiResponse(false, true, e.getMessage());
		}
	}

	@Override
	public ApiResponse getHolgingStokDetails(String token) {

        SmartConnect connect=new SmartConnect();
        connect.setApiKey(apiKey);
        connect.setAccessToken(token);
        JSONObject stockDetails = connect.getHolding();
		Map<String, Object> dataMap = stockDetails.toMap();
		return new ApiResponse(true, false, dataMap);
		}

}
