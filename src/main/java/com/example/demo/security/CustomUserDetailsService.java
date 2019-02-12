package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.repo.IUserRepo;
import com.example.demo.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private IUserRepo userRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Called with username {}", username);
		
		try {
			User user = new User();
			user = userRepo.findByEmail(username);
			
			UserBuilder builder = null;
			
		    if(user != null) {
		    	builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
		    	builder.roles(user.getRole().get(0).getName());
		    	builder.password(user.getPassword());
		    	builder.username(user.getEmail());
		    	
		    	return builder.build();
		    	}
		    else {
		    	throw new CustomException("User not found.");
		    	}
		    } catch (Exception e) {
		    	//e.printStackTrace();
		    	throw new UsernameNotFoundException(e.toString());
		    	}
		}
	}
