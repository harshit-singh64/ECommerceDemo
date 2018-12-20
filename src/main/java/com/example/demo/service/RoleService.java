package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.IRoleRepo;
import com.example.demo.repo.IUserRepo;

@Service
public class RoleService {
	
	@Autowired
	private IRoleRepo roleRepo;
	
	/*public List<Role> displayAll() {
		return roleRepo.findAll();
	}*/
	
	public List<RoleDto> roleDtoAssembler() {
		
		List<Role> roleList = roleRepo.findAll();
		List<RoleDto> roleDtoList = new ArrayList<>();
		
		for(Role role: roleList) {
			RoleDto roleDto = new RoleDto();
			roleDto.setId(role.getId());
			roleDto.setName(role.getName());
			
			roleDtoList.add(roleDto);
		}
		return roleDtoList;
	}
	
	/*public Role displayById(Integer id) {
		return roleRepo.findById(id).get();
	}
	
	public Role insertRole(@RequestBody Role role) {
	    return roleRepo.save(role);
	}
	
	public List<Role> getRole(List<Role> list) {
		return roleRepo.findAll();
	}
	
	public void deleteStudent(@PathVariable(value = "id") Integer userId) {
		roleRepo.deleteById(userId);
	}*/

}
