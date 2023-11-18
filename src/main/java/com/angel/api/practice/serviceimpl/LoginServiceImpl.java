package com.angel.api.practice.serviceimpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.LoginRequest;
import com.angel.api.practice.model.LoginStatus;
import com.angel.api.practice.model.LoginToken;
import com.angel.api.practice.repository.ILoginStatusRepository;
import com.angel.api.practice.repository.ILoginTokenRepository;
import com.angel.api.practice.service.ILoginService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class LoginServiceImpl implements ILoginService {
	
	@Autowired
	ILoginTokenRepository loginTokenRepository;
	
	@Autowired
	ILoginStatusRepository loginStatusRepository;
	
    private static String jwtToken=null;
    private static String refreshToken=null;
    private final ObjectMapper objectMapper = new ObjectMapper();

	@SuppressWarnings("deprecation")
	@Override
	public ApiResponse login(LoginRequest login) {
		 LoginToken loginToken = new LoginToken();
		 LoginStatus loginStatus = new LoginStatus();
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
                   
            String requestBodyJson =objectMapper.writeValueAsString(login);
              
            RequestBody body = RequestBody.create(mediaType, requestBodyJson);
            
            Request request = new Request.Builder().url("https://apiconnect.angelbroking.com/rest/auth/angelbroking/user/v1/loginByPassword")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("X-UserType", "USER")
                    .addHeader("X-SourceID", "WEB")
                    .addHeader("X-ClientLocalIP", "CLIENT_LOCAL_IP")
                    .addHeader("X-ClientPublicIP", "CLIENT_PUBLIC_IP")
                    .addHeader("X-MACAddress", "MAC_ADDRESS")
                    .addHeader("X-PrivateKey", "RCvbyZRP")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

             // Parse the JSON response body
                JsonNode responseJson = objectMapper.readTree(responseBody);

                // Extract status, errorcode, and message
                loginStatus.setStatus(responseJson.get("status").asBoolean());
                loginStatus.setErrorCode( responseJson.get("errorcode").asText());
                loginStatus.setMessage(responseJson.get("message").asText());
                loginToken.setLoginStatus(loginStatus);
                // Get the "data" object
                JsonNode dataNode = responseJson.get("data");
                
                if(!dataNode.isEmpty()) {
                    // Create a LoginToken object and map data from dataNode
                    
                    loginToken.setJwtToken(dataNode.get("jwtToken").asText());
                    loginToken.setRefreshToken(dataNode.get("refreshToken").asText());
                    loginToken.setFeedToken(dataNode.get("feedToken").asText());
                    
                    jwtToken=dataNode.get("jwtToken").asText();
                    refreshToken=dataNode.get("refreshToken").asText();
                    
                    // Print the jwtToken
                    System.out.println("jwtToken: " + jwtToken);
                refreshToken = dataNode.get("refreshToken").asText();
                }
                
             // Save the LoginToken object to the repository
                loginTokenRepository.save(loginToken);

                return new ApiResponse(true, false, responseJson);
            } else {
                return new ApiResponse(false, false, "Request failed with response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse(false, true, e.getMessage());
        }
	}
	
	
	@Override
    public ApiResponse getToken(String token) {
        
		 // Print the refreshToken
        System.out.println("refreshToken: " + refreshToken);
        try {
            
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");

			RequestBody body = RequestBody.create(mediaType, token);
            
            Request request = new Request.Builder().url("https://apiconnect.angelbroking.com/rest/auth/angelbroking/jwt/v1/generateTokens")
            		  .method("POST", body)
            		  .addHeader("Authorization", "Bearer "+refreshToken)
            		  .addHeader("Content-Type", "application/json")
            		  .addHeader("Accept", "application/json")
            		  .addHeader("X-UserType", "USER")
            		  .addHeader("X-SourceID", "WEB")
            		  .addHeader("X-ClientLocalIP", "CLIENT_LOCAL_IP")
            		  .addHeader("X-ClientPublicIP", "CLIENT_PUBLIC_IP")
            		  .addHeader("X-MACAddress", "MAC_ADDRESS")
            		  .addHeader("X-PrivateKey", "RCvbyZRP")
            		  .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                
             // Parse the JSON response body
                JsonNode responseJson = objectMapper.readTree(responseBody);
             
             // Get the "data" object
                JsonNode dataNode = responseJson.get("data");
             
                if(!dataNode.isEmpty()) {
             // Get the "jwtToken" value from the "data" object
                 jwtToken = dataNode.get("jwtToken").asText();
                }
                return new ApiResponse(true, false, responseJson);
            } else {
                return new ApiResponse(false, false, "Request failed with response code: " + response.code());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, true, e.getMessage());        }

    }
	

	@Override
	public ApiResponse userProfile() {
		try {
		    OkHttpClient client = new OkHttpClient();
		    Request request = new Request.Builder().url("https://apiconnect.angelbroking.com/rest/secure/angelbroking/user/v1/getProfile")
		    	  .method("GET", null)
          		  .addHeader("Authorization", "Bearer "+loginTokenRepository.getLiveToken())
          		  .addHeader("Content-Type", "application/json")
          		  .addHeader("Accept", "application/json")
          		  .addHeader("X-UserType", "USER")
          		  .addHeader("X-SourceID", "WEB")
          		  .addHeader("X-ClientLocalIP", "CLIENT_LOCAL_IP")
          		  .addHeader("X-ClientPublicIP", "CLIENT_PUBLIC_IP")
          		  .addHeader("X-MACAddress", "MAC_ADDRESS")
          		  .addHeader("X-PrivateKey", "RCvbyZRP")
          		  .build();
		    Response response = client.newCall(request).execute();

		    if (response.isSuccessful()) {
		        String responseBody = response.body().string();

		        // Parse the JSON response body
		        JsonNode responseJson = objectMapper.readTree(responseBody);

		        return new ApiResponse(true, false, responseJson);
		    } else {
		        return new ApiResponse(false, false, "Request failed with response code: " + response.code());
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    return new ApiResponse(false, true, e.getMessage());
		}
	}
	
   
}
