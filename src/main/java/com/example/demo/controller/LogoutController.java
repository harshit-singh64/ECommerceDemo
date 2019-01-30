package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.CustomException;
import com.example.demo.util.UtilResponse;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/api")
public class LogoutController {
	
	Jedis jedis = new Jedis("127.0.0.1", 6379);//"127.0.0.1", 6379
	
	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) throws CustomException {
		try {
			String token = httpServletRequest.getHeader("Authorization");
			System.out.println("token " + token);
			String username = jedis.get(token);
			System.out.println("username " + username);
			
			if(username != null) {
				jedis.del(token);
				
				HttpHeaders responseHeader = new HttpHeaders();
				UtilResponse utilResponse = new UtilResponse();
				
				utilResponse.setStatus(HttpStatus.CREATED.toString());
				utilResponse.setMessage("You have been logged out Sucessfully!");
				return new ResponseEntity<>(utilResponse, responseHeader, HttpStatus.OK);
				}
			else {
				throw new CustomException(400,"Invalid Access","you are not allowed in this area");
				}
			} catch (Exception e) {
			throw new CustomException(400,"Invalid Input","Invalid token input");
			}
		}
	
	/*@DeleteMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) throws CustomException {
		try {
			String token = httpServletRequest.getHeader("Authorization");
			System.out.println("token " + token);
			String username = jedis.get(token);
			System.out.println("username " + username);
			
			if(username != null) {
				System.out.println("username " + username);
				UserDto userDto =new UserDto();
				userDto = jwtTokenDecoder.tokenDecoder(token);
				System.out.println("token decoder values " + userDto);
				
				jedis.del(token);
				
				HttpHeaders responseHeader = new HttpHeaders();
				UtilResponse utilResponse = new UtilResponse();
				utilResponse.setStatus(HttpStatus.CREATED.toString());
				utilResponse.setMessage("You have been logged out Sucessfully!");
				return new ResponseEntity<>(utilResponse, responseHeader, HttpStatus.OK);
			}
			else {
				throw new CustomException(400,"Invalid Access","you are not allowed in this area");
			}
				
		} catch (Exception e) {
			throw new CustomException(400,"Invalid Input","Invalid token input");
		}
	}*/
}
