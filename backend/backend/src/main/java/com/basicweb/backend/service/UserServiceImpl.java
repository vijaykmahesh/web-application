package com.basicweb.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.basicweb.backend.DTO.UserDTO;
import com.basicweb.backend.entity.User;
import com.basicweb.backend.exception.DataNotFoundException;
import com.basicweb.backend.exception.DuplicateDataException;
import com.basicweb.backend.exception.ErrorWhileCreatingException;
import com.basicweb.backend.exception.InvalidInputException;
import com.basicweb.backend.mapping.UserMapping;
import com.basicweb.backend.repository.UserRepository;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;

	public UserDTO createUser(UserDTO userDTO) {

		User userCreated = null;

		Optional<User> optEmail = userRepository.findByEmailIdIgnoreCase(userDTO.getEmailId());

		if (!optEmail.isPresent()) {
			User user = UserMapping.toUser(userDTO);

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
			user.setPassword(hashedPassword);
			userCreated = userRepository.save(user);
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

}
