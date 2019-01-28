package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.jwt.JwtTokenGenerator;
import com.example.demo.login.LoginResponse;
import com.example.demo.login.UserCredentials;
import com.example.demo.service.ILoginService;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private ILoginService loginService;
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	Jedis jedis = new Jedis("127.0.0.1", 6379);//"127.0.0.1", 6379
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserCredentials userCredentials) throws CustomException {
		HttpHeaders responseHeader = new HttpHeaders();
		LoginResponse loginResponse = new LoginResponse();
		UserDto userDto = new UserDto();
		
		try {
			System.out.println(userCredentials.getUsername()+"=========="+ userCredentials.getPassword());
			
			userDto = loginService.login(userCredentials.getUsername(), userCredentials.getPassword());
			
			if(userDto != null) {
				String token = jwtTokenGenerator.tokenGenerator(userDto);
				System.out.println("token generated........... ");
				jedis.set(token, userCredentials.getUsername());
				
				loginResponse.setStatus(HttpStatus.CREATED.toString());
				loginResponse.setToken(token);
				loginResponse.setMessage(HttpStatus.OK.toString());
				System.out.println("login sucess.................. and token generated");
				}
			/*else {
				loginResponse.setToken("Please check your credentials");
				loginResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
				System.out.println("else block : " + HttpStatus.UNAUTHORIZED.toString());
				}*/
			} 
		catch (NullPointerException e) {
			//e.printStackTrace();
			throw new CustomException(400,e.toString());
			
			/*loginResponse.setToken(null);
			loginResponse.setMessage("Please check your credentials");
			loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());*/
			}
		catch (CustomException e) {
			//e.printStackTrace();
			throw new CustomException(400,e.toString());
			
			/*loginResponse.setToken(null);
			loginResponse.setMessage("Please check your credentials");
			loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());*/
			}
		return new ResponseEntity<>(loginResponse, responseHeader, HttpStatus.OK);
		}
	}
