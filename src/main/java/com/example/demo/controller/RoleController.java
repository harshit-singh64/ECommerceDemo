package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RoleDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/role")
	public RoleDto insert(@RequestBody @Valid RoleDto roleDto) throws InvalidInputException, CustomException {
		return roleService.insert(roleDto);
	}
	
	@GetMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RoleDto> getAll() {
		return roleService.getAll();
	}
	
	@GetMapping(value = "/role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RoleDto getById(@PathVariable(value = "id") @Valid Integer roleId) {
		return roleService.getById(roleId);
	}
	
	@PutMapping("/role")
	public RoleDto update(@RequestBody @Valid RoleDto roleDto) throws InvalidInputException {
		return roleService.update(roleDto);
	}
	
	@DeleteMapping("/role/{id}")
	public String delete(@PathVariable(value = "id") Integer roleId) {
		roleService.delete(roleId);
		return "deleted";
	}
	
	/*@GetMapping("/role/{id}")
	public Role getRoleById(@PathVariable(value = "id") Integer roleId) {
		return roleService.displayById(roleId);
	}
	
	@PostMapping("/role")
	public Role createRole(@RequestBody Role role) {
	    return roleService.insertRole(role);
	}
	
	@DeleteMapping("/role/{id}")
	public void deleteRole(@PathVariable(value = "id") Integer studentId) {
		roleService.deleteStudent(studentId);
	}*/
}
