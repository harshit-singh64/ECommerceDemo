package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.InvalidInputException;

public interface IUserService {
	UserDto insertUser(UserDto userDto) throws InvalidInputException;
	List<UserDto> displayAllUsers();
	UserDto displayById(Integer id);
	UserDto updateUser(UserDto userDto) throws InvalidInputException;
	String delete(Integer userId);
}
