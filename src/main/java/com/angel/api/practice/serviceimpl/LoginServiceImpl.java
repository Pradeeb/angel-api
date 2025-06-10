package com.angel.api.practice.serviceimpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.LoginRequest;
import com.angel.api.practice.model.LoginStatus;
import com.angel.api.practice.model.LoginToken;
import com.angel.api.practice.repository.ILoginStatusRepository;
import com.angel.api.practice.repository.ILoginTokenRepository;
import com.angel.api.practice.service.ILoginService;
import com.angelbroking.smartapi.SmartConnect;
import com.angelbroking.smartapi.models.User;
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
	
    @Value("${api.key}")
    private String apiKey;
    
    private static String jwtToken=null;
    private static String refreshToken=null;
    private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ApiResponse login(LoginRequest login) {
		 LoginToken loginToken = new LoginToken();
		 LoginStatus loginStatus = new LoginStatus();
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
                   
            String requestBodyJson =objectMapper.writeValueAsString(login);
              
            RequestBody body = RequestBody.create(mediaType, requestBodyJson);
            
            SmartConnect connect=new SmartConnect();
            connect.setApiKey(apiKey);
            User user = connect.generateSession(login.getClientcode().toString(), login.getPassword().toString(), login.getTotp().toString());

            if (user != null) {


                // Extract status, errorcode, and message
                loginStatus.setStatus(user != null);
                loginStatus.setErrorCode("200");
                loginStatus.setMessage("Login successful");
                loginToken.setLoginStatus(loginStatus);
                // Get the "data" object
                loginToken.setJwtToken(user.getAccessToken());
                loginToken.setRefreshToken(user.getRefreshToken());
                loginToken.setFeedToken(user.getFeedToken());
                
             // Save the LoginToken object to the repository
                loginTokenRepository.save(loginToken);

                return new ApiResponse(user != null, user == null, user);
            } else {
                return new ApiResponse(user != null, user == null, "Request failed with response code: " +"401");
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
