package com.basicweb.backend.mapping;

import com.basicweb.backend.DTO.UserDTO;
import com.basicweb.backend.entity.User;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class UserMapping {


	public static UserDTO toUserDTO(final User user) {

		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		mapperFactory.classMap(User.class, UserDTO.class).byDefault().register();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		UserDTO dto = mapper.map(user, UserDTO.class);
		return dto;
	}
	
	public static User toUser(final UserDTO userDTO) {

		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		mapperFactory.classMap(UserDTO.class, User.class).byDefault().register();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		User dto = mapper.map(userDTO, User.class);
		return dto;
	}

}
