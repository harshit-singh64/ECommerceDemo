package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;

public interface IUserService {
	UserDto insertUser(UserDto userDto) throws InvalidInputException, CustomException;
	List<UserDto> displayAllUsers();
	UserDto updateUser(@RequestBody @Valid UserDto userDto) throws InvalidInputException;
	String delete(Integer userId);
}
