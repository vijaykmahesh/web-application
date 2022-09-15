package com.basicweb.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.basicweb.backend.DTO.UserDTO;
import com.basicweb.backend.service.UserServiceImpl;


@RestController
public class LoginController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	 @CrossOrigin(origins = "http://localhost:4200")
	 @GetMapping("/login")
	public @ResponseBody UserDTO userOAuthLogin(
			@RequestParam(value = "emailId", required = true) final String emailId,
			@RequestParam(value = "password", required = true) final String password) throws Exception {
		
			return userServiceImpl.login(emailId, password);
	}
	
	

}
