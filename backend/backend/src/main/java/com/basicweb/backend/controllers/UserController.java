package com.basicweb.backend.controllers;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.basicweb.backend.DTO.UserDTO;
import com.basicweb.backend.exception.DuplicateDataException;
import com.basicweb.backend.service.UserServiceImpl;


@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/services/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping(value = { "/createUser" }, method = { RequestMethod.POST }, produces = "application/json")
	public UserDTO createUser(@RequestBody UserDTO userDTO) {
		
		try {
			return userService.createUser(userDTO);
		} catch (DataIntegrityViolationException e) {
			if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new DuplicateDataException("Duplicate Email Address or User Name");
			}
			throw e;
		}
	}



}
