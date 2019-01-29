package com.example.demo.jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenDecoder {
	
	@Value("${spring.security.signing-key}")
	private String secretKey;
	
	@SuppressWarnings({ "unchecked"})
	public UserDto tokenDecoder(String token) throws CustomException {
		UserDto userDto = new UserDto();
		try {
			System.out.println("token from tokenDecoder "+token);
			
			Claims claims = Jwts
					.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();
			
			userDto.setEmail(claims.getSubject());
			userDto.setId((Integer) claims.get("id"));
			userDto.setName((String) claims.get("name"));
			//userDto.setPhoneNumber((String) claims.get("phoneNumber"));
			userDto.setRoleDto((List<RoleDto>) claims.get("role"));
			
			/*@SuppressWarnings("rawtypes")
			ArrayList roleNameList = (ArrayList) claims.get("role");
			LinkedHashMap<Object, Object> roleNameMap = (LinkedHashMap<Object, Object>) roleNameList.get(0);
			System.out.println(roleNameMap.get("name")+"==============name");*/
		} catch (Exception e) {
			//e.printStackTrace();
			throw new CustomException(500,"error in deocoding",e.toString());
			}
		return userDto;
		}
	}
