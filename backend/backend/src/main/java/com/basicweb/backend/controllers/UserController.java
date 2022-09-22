package com.basicweb.backend.controllers;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.basicweb.backend.DTO.UserDTO;
import com.basicweb.backend.exception.DuplicateDataException;
import com.basicweb.backend.service.UserServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/services/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	
	 @PostMapping("/createUser")
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
	 
	
	 @PostMapping("/uploadFile")
	public UserDTO uploadFile(@RequestParam("emailId") String emailId, @RequestParam("file") MultipartFile file) throws IOException {
		
		System.out.println(file);
		
		return userService.uploadFile(emailId, file);

	}
	 
	
	 @RequestMapping(value = "/downloadImage/{imgName}", method = RequestMethod.GET,
     produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imgName") String imgName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource = userService.getResource(imgName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource,response.getOutputStream());

	}
	 
	 @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
			public UserDTO getUserById(@RequestParam("userId") Long userId) {
				
				return userService.getUserById(userId);
				
				
			}



}
