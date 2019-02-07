package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	//@Autowired
	//private AuthenticationManager authenticationManager;
	 
	Jedis jedis = new Jedis("127.0.0.1", 6379);//"127.0.0.1", 6379
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserCredentials userCredentials) throws CustomException {
		HttpHeaders responseHeader = new HttpHeaders();
		LoginResponse loginResponse = new LoginResponse();
		UserDto userDto = new UserDto();
		String token = null;
		
		try {
			System.out.println(userCredentials.getUsername()+"=========="+ userCredentials.getPassword());
			
			userDto = loginService.login(userCredentials.getUsername(), userCredentials.getPassword());
			
			if(userDto != null) {
				token = jwtTokenGenerator.tokenGenerator2(userDto);
				System.out.println("token generated........... ");
				jedis.set(token, userCredentials.getUsername());
				
				/*final Authentication authentication = authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                		userCredentials.getUsername(),
		                		userCredentials.getPassword()
		                )
		        );
		        SecurityContextHolder.getContext().setAuthentication(authentication);*/
				
				loginResponse.setStatus(HttpStatus.CREATED.toString());
				loginResponse.setToken(token);
				loginResponse.setMessage("SUCESS");
				System.out.println("login sucess.................. and token generated");
				return new ResponseEntity<>(loginResponse, responseHeader, HttpStatus.OK);
				}
			else {
				loginResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
				loginResponse.setToken(token);
				loginResponse.setMessage("FALIURE");
				System.out.println("else block : " + HttpStatus.UNAUTHORIZED.toString());
				return new ResponseEntity<>(loginResponse, responseHeader, HttpStatus.OK);
				}
			} 
		catch (NullPointerException e) {
			//e.printStackTrace();
			throw new CustomException(400,"Invalid Input",e.toString());
			
			/*loginResponse.setToken(null);
			loginResponse.setMessage("Please check your credentials");
			loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());*/
			}
		catch (CustomException e) {
			//e.printStackTrace();
			throw new CustomException(400,"Invalid Input",e.toString());
			
			/*loginResponse.setToken(null);
			loginResponse.setMessage("Please check your credentials");
			loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());*/
			}
		//return new ResponseEntity<>(loginResponse, responseHeader, HttpStatus.OK);
		}
	}
