package com.example.demo.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenDecoder {
	@Value("${spring.security.signing-key}")
	private String secretKey;
	
	//String secretKey = "my-secret-token-to-change-in-production";
	
	public UserDto tokenDecoder(String token) throws CustomException {
		UserDto userDto = new UserDto();
		
		try {
			System.out.println(token);
			Claims claims = Jwts
					.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();
			
			//int id = (int) claims.get("id");
			
			userDto.setEmail(claims.getSubject());
			userDto.setId((Integer) claims.get("id"));
			userDto.setPassword(claims.get("password").toString());
			System.out.println(claims);
		} catch (Exception e) {
			System.out.println(userDto);
			throw new CustomException(500,"error in deocoding");
			}
		return userDto;
		}
	}
