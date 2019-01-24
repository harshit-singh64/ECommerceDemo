package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;

public interface ILoginService {
	UserDto login(String userName, String password) throws CustomException;
}
