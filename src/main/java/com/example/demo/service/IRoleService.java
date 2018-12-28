package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RoleDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;

public interface IRoleService {
	RoleDto insert(RoleDto roleDto) throws InvalidInputException, CustomException;
	List<RoleDto> getAll();
	RoleDto getById(Integer id);
	RoleDto update(RoleDto roleDto) throws InvalidInputException;
	String delete(Integer roleId);
}
