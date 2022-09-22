package com.basicweb.backend.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.basicweb.backend.DTO.UserDTO;
import com.basicweb.backend.constants.Constant;
import com.basicweb.backend.entity.Image;
import com.basicweb.backend.entity.User;
import com.basicweb.backend.exception.DataNotFoundException;
import com.basicweb.backend.exception.DuplicateDataException;
import com.basicweb.backend.exception.ErrorWhileCreatingException;
import com.basicweb.backend.exception.InvalidInputException;
import com.basicweb.backend.mapping.UserMapping;
import com.basicweb.backend.repository.ImageRepository;
import com.basicweb.backend.repository.UserRepository;
import com.sendgrid.Response;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageServiceImpl imageServiceImpl;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;

	public UserDTO createUser(UserDTO userDTO) {

		User userCreated = null;

		Optional<User> optEmail = userRepository.findByEmailIdIgnoreCase(userDTO.getEmailId());

		if (!optEmail.isPresent()) {
			User user = UserMapping.toUser(userDTO);

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
			user.setPassword(hashedPassword);
		
			userCreated = userRepository.save(user);
			System.out.println(env.getProperty("spring.mail.apiKey"));
			
			try {
				Response response =  emailServiceImpl.sendTextEmail(user.getEmailId(), userDTO.getPassword());
				
				if(response.getStatusCode()==200) {
					System.out.println(response.getBody());
				}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			if (userCreated == null) {
				throw new ErrorWhileCreatingException("Error while creating user");
			}
			return UserMapping.toUserDTO(userCreated);
		} else {
			throw new DuplicateDataException("Email Id already exist");
		}
	}

	public UserDTO login(String emailId, String password) {

		Optional<User> optionalUser = null;

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (emailId != null && password != null) {
			optionalUser = userRepository.findByEmailIdIgnoreCase(emailId);

			if (optionalUser.isPresent()) {
				User user = optionalUser.get();

				boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());

				if (isPasswordMatch) {
					UserDTO userDTO = UserMapping.toUserDTO(user);
					return userDTO;
				} else {
					throw new InvalidInputException("Invalid Credentials");
				}

			} else {
				throw new DataNotFoundException("User Not Found with EmailId" + emailId);
			}
		}
		else {
			throw new InvalidInputException("Not a valid Input");
		}

	}

	public UserDTO uploadFile(String emailId, MultipartFile file) throws IOException {
		
		System.out.println(env.getProperty("project.image"));
		
		Image img = null;
		
		Optional<User> userOptional =  userRepository.findByEmailIdIgnoreCase(emailId);
		
		img = userOptional.get().getImage();
		
		if(userOptional.isPresent() && img != null) {
			
			String fileName1  = imageServiceImpl.getOriginalFileName(file, file.getOriginalFilename());
			
			img.setImageName(fileName1);
			
			img.setImagePath(Constant.IMG_PATH+fileName1);
			
			img = imageRepository.save(img);
			
			if(img != null) {
				UserDTO userDTO = UserMapping.toUserDTO(userOptional.get());
				return userDTO;
			}
			else 
				throw new ErrorWhileCreatingException("Error while Uploading File");
			
		}else if(img == null) {
			String fileName1  = imageServiceImpl.getOriginalFileName(file, file.getOriginalFilename());
			
			User user =  userOptional.get();
			
			img = new Image();
			img.setImageName(fileName1);
			img.setImagePath(Constant.IMG_PATH+fileName1);
			
			user.setImage(img);
			
			user =  userRepository.save(user);
			
			UserDTO userDTO = UserMapping.toUserDTO(user);
			return userDTO;
			
		}
		else
			throw new DataNotFoundException("User Not Found with EmailId" + emailId);
		
	}

	public InputStream getResource(String imgName) throws FileNotFoundException {
		
		
		String fullPath =  env.getProperty("project.image")+File.separator+imgName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

	public UserDTO getUserById(Long userId) {
		
		Optional<User> optionalUser  = userRepository.findById(userId);
		
		if(optionalUser.isPresent()) {
			
			return UserMapping.toUserDTO(optionalUser.get());
		}
		else
			throw new DataNotFoundException("User Not Found with userId" + userId);
	}

}
