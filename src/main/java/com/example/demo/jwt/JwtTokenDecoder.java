package com.example.demo.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenDecoder implements Serializable {
	@Value("${spring.security.signing-key}")
	private String secretKey;
	
	/*@SuppressWarnings({ "unchecked"})
	public HashMap<UserDto, Date> tokenDecoder(String token) throws CustomException {
		HashMap<UserDto, Date> dtoMap = new HashMap<>();
		UserDto userDto = new UserDto();
		
		System.out.println("token from tokenDecoder : "+token);
		
		Claims claims = Jwts
				.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		
		Date currentDateAndTime = new Date();
		Date expirationDateAndTime = claims.getExpiration();
		
		userDto.setEmail(claims.getSubject());
		userDto.setId((Integer) claims.get("id"));
		userDto.setName((String) claims.get("name"));
		userDto.setPhoneNumber((String) claims.get("phoneNumber"));
		userDto.setRoleDto((List<RoleDto>) claims.get("role"));
		
		dtoMap.put(userDto, claims.getExpiration());
		System.out.println("dtoMap : "+dtoMap);
		
		return dtoMap;
		}*/
	
	@SuppressWarnings({ "unchecked" })
	public String tokenDecoder(String token) throws CustomException {
		UserDto userDto = new UserDto();
		try {
			System.out.println("token from tokenDecoder (String) " + token);

			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

			Date currentDateAndTime = new Date();
			Date expirationDateAndTime = claims.getExpiration();

			/*System.out.println("current : " + currentDateAndTime);
			System.out.println("expiry : " + expirationDateAndTime);*/

			if (expirationDateAndTime.after(currentDateAndTime)) {
				userDto.setEmail(claims.getSubject());
				userDto.setId((Integer) claims.get("id"));
				userDto.setName((String) claims.get("name"));
				userDto.setPhoneNumber((String) claims.get("phoneNumber"));
				userDto.setRoleDto((List<RoleDto>) claims.get("role"));
				return userDto.getEmail();
			} else {
				throw new CustomException("Token Expired");
			}

			
		} catch (Exception e) { 
			// e.printStackTrace(); 
			throw new CustomException(500,"error in deocoding",e.toString()); } }
	 
	

	@SuppressWarnings({ "unchecked" })
	public UserDto tokenDecoderDto(String token) throws CustomException {
		UserDto userDto = new UserDto();
		try {
			System.out.println("token from tokenDecoder " + token);

			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

			Date currentDateAndTime = new Date();
			Date expirationDateAndTime = claims.getExpiration();

			System.out.println("current : " + currentDateAndTime);
			System.out.println("expiry : " + expirationDateAndTime);

			if (expirationDateAndTime.after(currentDateAndTime)) {
				userDto.setEmail(claims.getSubject());
				userDto.setId((Integer) claims.get("id"));
				userDto.setName((String) claims.get("name"));
				userDto.setPhoneNumber((String) claims.get("phoneNumber"));
				userDto.setRoleDto((List<RoleDto>) claims.get("role"));
				return userDto;
			} else {
				throw new CustomException("Token Expired");
			}

			
		} catch (Exception e) { 
			// e.printStackTrace(); 
			throw new CustomException(500,"error in deocoding",e.toString()); }
		}
}
	 