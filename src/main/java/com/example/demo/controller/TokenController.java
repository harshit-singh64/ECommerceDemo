package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.jwt.JwtTokenDecoder;
import com.example.demo.util.UtilResponse;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/token")
public class TokenController {
	@Autowired
	private JwtTokenDecoder jwtTokenDecoder;
	
	private Jedis jedis = new Jedis("127.0.0.1", 6379);
	
	@GetMapping("/decode")
	public ResponseEntity<?> decode(HttpServletRequest httpServletRequest) throws CustomException {
		try {
			String token = httpServletRequest.getHeader("Authorization");
			String username = jedis.get(token);
			UserDto userDto = new UserDto();
			
			if(username != null) {
				userDto = jwtTokenDecoder.tokenDecoderDto(token);
				return new ResponseEntity<>(userDto, HttpStatus.OK);
			}
			else {
				UtilResponse utilResponse = new UtilResponse();
				utilResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
				utilResponse.setMessage("Invalid Token");
				return new ResponseEntity<>(utilResponse, HttpStatus.BAD_REQUEST);
				}
			} 
		catch (CustomException e) {
			throw e;
			}
		catch (NullPointerException e) {
			throw e;
			}
		catch (Exception e) {
			throw e;
			}
		}
	}
