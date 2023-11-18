package com.angel.api.practice.controller.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.angel.api.practice.dao.UserDetails;
import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.LoginRequest;
import com.angel.api.practice.service.ILoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(path = "/")
public class WebAppController {

	@Autowired
	private ILoginService loginService;

	@GetMapping
	public String LoginForm() {
		System.out.println("come to web home.");
		return "index";
	}

	@PostMapping(path = "/dashboard")
	public String LoginRegister(@ModelAttribute LoginRequest user, Model model) throws JsonMappingException, JsonProcessingException {

		ApiResponse loginResponse = loginService.login(user);
		model.addAttribute("loginResponse", loginResponse);
		
		String loginresponse = loginResponse.getData().toString();

		// Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON response
        JsonNode jsonNode = objectMapper.readTree(loginresponse);

        // Extract the "status" field
        boolean status = jsonNode.get("status").asBoolean();

        // Now, you can use the "status" value
        if (status) {
        	return "dashboard";
        } else {
            String errorMessage = jsonNode.get("message").asText();
            System.out.println("Login failed: " + errorMessage);
            model.addAttribute("error", errorMessage);
            return "index";
        }

	}

	@GetMapping(path = "/userProfile")
	public String getUserProfile(Model model) throws IOException {
		ApiResponse userProfile = loginService.userProfile();
		System.out.println(userProfile);
		String details=userProfile.getData().toString();
		
		// Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON response
        JsonNode jsonNode = objectMapper.readTree(details);
        
        JsonNode dataNode = jsonNode.get("data");
        
        UserDetails detail=new UserDetails();
        
        detail.setClientCode(dataNode.get("clientcode").asText());
        detail.setName(dataNode.get("name").asText());
        detail.setExchanges(dataNode.get("exchanges").toString());
        detail.setProducts(dataNode.get("products").toString());
        detail.setLastLoginTime(dataNode.get("lastlogintime").asText());
        
		model.addAttribute("userdetail", detail);
		return "user";
	}
}
