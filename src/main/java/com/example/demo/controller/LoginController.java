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
import com.example.demo.jwt.JwtGenerator;
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
	private JwtGenerator jwtGenerator;
	
	Jedis jedis = new Jedis("127.0.0.1", 6379);//"127.0.0.1", 6379
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserCredentials userCredentials) {
		LoginResponse loginResponse = new LoginResponse();
		UserDto userDto = new UserDto();
		
		try {
			
			userDto = loginService.login(userCredentials.getUsername(), userCredentials.getPassword());
			
			if(userDto != null) {
				String token = jwtGenerator.tokenGenerator(userDto);
				
				jedis.set(token, userCredentials.getUsername());
				System.out.println(token);
				System.out.println(jedis.get(token));
				
				loginResponse.setStatus(HttpStatus.CREATED.toString());
				loginResponse.setToken(token);
				loginResponse.setMessage(HttpStatus.OK.toString());
				
				
				//jedis.set(token, userCredentials.getUsername());
				/*jedis.set(userCredentials.getUsername(), token);
				
				System.out.println(jedis.get(userCredentials.getUsername()));
				
				loginResponse.setToken(token);
				loginResponse.setStatus(HttpStatus.CREATED.toString());*/
				//System.out.println(HttpStatus.CREATED.toString());
				}
			/*else {
				loginResponse.setToken("Please check your credentials");
				loginResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
				System.out.println("else block : " + HttpStatus.UNAUTHORIZED.toString());
				}*/
			} catch (Exception e) {
				e.printStackTrace();
				loginResponse.setToken(null);
				loginResponse.setMessage("Please check your credentials");
				//loginResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
				loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				//System.out.println("cach block :" + HttpStatus.INTERNAL_SERVER_ERROR.toString());
				}
		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<>(loginResponse, responseHeader, HttpStatus.OK);
	}
	
	/*@PostMapping("/login")
	public String login(@Valid @RequestHeader(value = "userName") String userName,
			@RequestHeader(value = "password") String password) throws InvalidInputException {
		System.out.println("Searching by userName  : " + userName);
		loginService.login(userName, password);
		return "login sucess";
	}*/
}
