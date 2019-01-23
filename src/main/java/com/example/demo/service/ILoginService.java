package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserNotFoundException;

public interface ILoginService {
	UserDto login(String username, String password);
}
