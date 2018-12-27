package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RoleDto;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/role")
	public List<RoleDto> getAllRole() {
		return roleService.roleDtoAssembler();
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
