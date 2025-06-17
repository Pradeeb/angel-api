package com.angel.api.practice.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.LoginRequest;
import com.angel.api.practice.service.ILoginService;
import com.angel.api.practice.service.IStockDetails;
import com.angelbroking.smartapi.models.User;

@RestController
@RequestMapping(path = "/api")
public class RestApiController {

	@Autowired
	private ILoginService loginService;
	

	@Autowired
	private IStockDetails stockDetails;

	@ResponseBody
	@PostMapping(path = "/login", produces = "application/json", consumes = "application/json")
	public ApiResponse login(@RequestBody LoginRequest login,HttpServletResponse response ) {

		ApiResponse loginResponse = loginService.login(login);
		

	    if (loginResponse.isSuccess()) {
	        User user = (User) loginResponse.getData();

	        // Save access token in cookie
	        Cookie accessTokenCookie = new Cookie("accessToken", user.getAccessToken());
	        accessTokenCookie.setHttpOnly(true);       // JavaScript can't read it
	        accessTokenCookie.setPath("/");            // Valid for all paths
	        accessTokenCookie.setMaxAge(60 * 60);      // 1 hour
	        accessTokenCookie.setSecure(true); // Only over HTTPS
	        response.addCookie(accessTokenCookie);
	    }

		return loginResponse;
	}

	@ResponseBody
	@PostMapping(path = "/getToken", produces = "application/json", consumes = "application/json")
	public ApiResponse getToken(@org.springframework.web.bind.annotation.RequestBody String token) {
		System.out.println("come to login");
		ApiResponse loginResponse = loginService.getToken(token);

		return loginResponse;
	}

	@ResponseBody
	@GetMapping(path = "/userProfile")
	public ApiResponse getUserProfile() throws IOException {
		ApiResponse userProfile = loginService.userProfile();
		return userProfile;
	}
	
	@ResponseBody
	@GetMapping(path = "/getallholding")
	public ApiResponse getAllHolding(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
		ApiResponse userProfile = stockDetails.getAllHolgingStokDetails(authorizationHeader);
		return userProfile;
	}

	@ResponseBody
	@GetMapping(path = "/getholding")
	public ApiResponse getHolding(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
		ApiResponse userProfile = stockDetails.getHolgingStokDetails(authorizationHeader);
		return userProfile;
	}
	
}
