package com.basicweb.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.basicweb.backend.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{
	
	public Optional<User> findByEmailIdIgnoreCase(@Param("emailId") String emailId);

}
