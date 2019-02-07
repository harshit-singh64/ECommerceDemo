package com.example.demo.security;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class SecurityValidator {
	//@Value("${spring.security.signing-key}")
	private String secret="MaYzkSjmkzPC57L";
	
	
	public User validate(String token) throws CustomException {
		User user= new User();
		try {
		Claims body=Jwts.parser().
				setSigningKey(secret).
				parseClaimsJws(token).getBody();
		
		user.setEmail(body.getSubject());
		user.setId(Integer.parseInt((String) body.get("id")));
		user.setName((String) body.get("name"));
		user.setPassword((String) body.get("password"));
		user.setAssignedRole((String) body.get("department"));
		
		System.out.println(user+" security validator ");
		return user;
		
		}catch (Exception e) {
			System.out.println(e);
			throw new CustomException(e.toString());
		}
		
		
	}
}
