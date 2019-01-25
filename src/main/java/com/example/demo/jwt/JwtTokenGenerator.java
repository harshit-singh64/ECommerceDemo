package com.example.demo.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator {
	@Autowired
	private IUserService userService;
	
	@Value("${spring.security.signing-key}")
	private String secretKey;
	
	public String tokenGenerator(UserDto userDto) {
		User user = new User();
		user = userService.dtoToEntityAssembler(userDto, user);
		
		Date date = new Date();
		long time = date.getTime();
		Date expirationTime = new Date(time + 5l);//5 seconds
		System.out.println(date);
		System.out.println(time);
		System.out.println(expirationTime);
		
		//Header header = Jwts.header().setType("JWT");
		
		Claims claims=Jwts.claims().setSubject(userDto.getEmail()).setIssuedAt(date).setExpiration(expirationTime);
		claims.put("id",user.getId());
		claims.put("password", user.getPassword());
		claims.put("phoneNumber", user.getPhoneNumber());
		claims.put("role", user.getRole().get(0).getName());
		//claims.setExpiration(expirationTime);
		
		return Jwts.builder()
				//.setHeader()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		}
	}
