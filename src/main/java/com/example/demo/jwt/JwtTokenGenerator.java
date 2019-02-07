package com.example.demo.jwt;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

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
	private String secretKey = "MaYzkSjmkzPC57L";
	
	private String headerString = "Authorization";
	
	public void tokenGenerator(HttpServletResponse response, String name) {
		UserDto userDto = new UserDto();
		System.out.println("userdto from token generator: "+userDto);
		
		User user = new User();
		user = userService.dtoToEntityAssembler(userDto, user);
		
		System.out.println("user from token generator: "+user);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy HH:mm:ss zzz");
		Date date = new Date();
		long time = date.getTime();
		Date expirationTime = new Date(time + 1000000l);//5 seconds
		
		System.out.println("issued at : "+dateFormat.format(date));
		System.out.println("expires at : "+dateFormat.format(expirationTime));
		//System.out.println(date);
		//System.out.println(time);
		//System.out.println(expirationTime);
		
		//Header header = Jwts.header().setType("JWT");
		
		Claims claims=Jwts.claims()
				.setSubject(userDto.getEmail())
				.setIssuedAt(date)
				.setExpiration(expirationTime);
		
		claims.put("id",user.getId());
		claims.put("phoneNumber", user.getPhoneNumber());
		claims.put("name", user.getName());
		claims.put("role", user.getRole());
		//claims.setExpiration(expirationTime);
		
		String jwtToken = Jwts.builder()
				
				.setExpiration(expirationTime)
				//.setHeader()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		
		response.addHeader(headerString, jwtToken);
		}
	
	public String tokenGenerator2(UserDto userDto) {
		System.out.println("userdto from token generator: "+userDto);
		
		User user = new User();
		user = userService.dtoToEntityAssembler(userDto, user);
		
		System.out.println("user from token generator: "+user);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy HH:mm:ss zzz");
		Date date = new Date();
		long time = date.getTime();
		Date expirationTime = new Date(time + 1000000l);//5 seconds
		
		System.out.println("issued at : "+dateFormat.format(date));
		System.out.println("expires at : "+dateFormat.format(expirationTime));
		//System.out.println(date);
		//System.out.println(time);
		//System.out.println(expirationTime);
		
		//Header header = Jwts.header().setType("JWT");
		
		Claims claims=Jwts.claims()
				.setSubject(userDto.getEmail())
				.setIssuedAt(date)
				.setExpiration(expirationTime);
		
		claims.put("id",user.getId());
		claims.put("phoneNumber", user.getPhoneNumber());
		claims.put("name", user.getName());
		claims.put("role", user.getRole());
		//claims.setExpiration(expirationTime);
		
		return Jwts.builder()
				.setExpiration(expirationTime)
				//.setHeader()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		}
	}
