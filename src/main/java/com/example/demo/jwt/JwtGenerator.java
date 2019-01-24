package com.example.demo.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
	@Autowired
	private UserService userService;
	
	public String tokenGenerator(UserDto userDto) {
		User user = new User();
		user = userService.dtoToEntityAssembler(userDto, user);
		Date date = new Date();
		long time = date.getTime();
		Date expirationTime = new Date(time + 5l);//5 seconds
		
		Claims claims=Jwts.claims().setSubject(user.getEmail());
		claims.put("id",user.getId().toString());
		claims.put("password", user.getPassword());
		claims.setExpiration(expirationTime);
		
		return Jwts.builder().setClaims(claims).setExpiration(expirationTime)
				.signWith(SignatureAlgorithm.HS512, "my-secret-token-to-change-in-production")
				.compact();
		}
	}
