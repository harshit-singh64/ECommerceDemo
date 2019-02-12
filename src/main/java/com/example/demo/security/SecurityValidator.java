package com.example.demo.security;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.dto.RoleDto;
import com.example.demo.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class SecurityValidator {
	//@Value("${spring.security.signing-key}")
	private String secret="MaYzkSjmkzPC57L";
	
	
	public JwtUser validate(String token) throws CustomException {
		JwtUser jwtUser = null;
		try {
		Claims body=Jwts.parser().
				setSigningKey(secret).
				parseClaimsJws(token).getBody();
		
		jwtUser = new JwtUser();
		
		jwtUser.setUserName(body.getSubject());
		jwtUser.setId((Integer) body.get("id"));
		jwtUser.setRole((String) body.get("role"));
		jwtUser.setRoleDtoList((List<RoleDto>) body.get("roleList"));
		
		System.out.println(jwtUser.toString()+" security validator ");
		return jwtUser;
		
		}catch (Exception e) {
			System.out.println(e);
			throw new CustomException(e.toString());
		}
		
		
	}
}
