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

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private IUserRepo userRepo;
	
	//private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//logger.info("Called with username {}", username);
		
		System.out.println("username from CustomUserDetailsService >>>>>>> "+username);
		
		try {
			User user = new User();
			user = userRepo.findByEmail(username);
			
			System.out.println("user from CustomUserDetailsService >>>>>>> "+user);
			
			UserBuilder builder = null;
			
			System.out.println("bulder from CustomUserDetailsService >>>>>>> "+builder);
			
		    if(user != null) {
		    	builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
		    	builder.roles(user.getRole().get(0).getName());
		    	builder.password(user.getPassword());
		    	builder.username(user.getEmail());
		    	
		    	System.out.println("builder   >>>>"+builder.toString());
		    	System.out.println("builder   >>>>"+builder.build().toString());
		    	
		    	return builder.build();
		    	}
		    else {
		    	System.out.println("builder   >>>>"+builder);
		    	throw new CustomException("User not found.");
		    	}
		    } catch (Exception e) {
		    	System.out.println("CustomException >>>>");
		    	//e.printStackTrace();
		    	throw new UsernameNotFoundException(e.toString());
		    	}
		}
	}
