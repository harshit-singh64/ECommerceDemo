package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.service.IUserService;

@RestController
@RequestMapping("/signup")
public class SignUpController {
	@Autowired
	private IUserService userService;
	
	@PostMapping
	public UserDto insertUser(@RequestBody @Valid UserDto userDto) throws InvalidInputException, CustomException, 
	UnsupportedEncodingException {
		return userService.insertUser(userDto);
		}
}
