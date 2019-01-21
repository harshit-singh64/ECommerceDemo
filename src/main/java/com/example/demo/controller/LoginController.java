package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidInputException;
import com.example.demo.login.LoginService;

@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public String login(@Valid @RequestHeader(value = "userName") String userName,
			@RequestHeader(value = "password") String password) throws InvalidInputException {
		System.out.println("Searching by userName  : " + userName);
		loginService.login(userName, password);
		return "login sucess";
	}
}
