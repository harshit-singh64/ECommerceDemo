package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RoleDto;
import com.example.demo.entity.Role;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.repo.IRoleRepo;

@Service
public class RoleService implements IRoleService{
	@Autowired
	private IRoleRepo roleRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleService.class.getName());
	
	public RoleDto entityToDtoAssembler(RoleDto roleDto, Role role) {
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		//roleDto.setUserDto(role.getUser());
		return roleDto;
	}
	
	public Role dtoToEntityAssembler(RoleDto roleDto, Role role) {
		role.setId(roleDto.getId());
		role.setName(roleDto.getName());
		//role.setUser(roleDto.getUserDto());
		return role;
	}
	
	/*inserting value*/
	
	public RoleDto insert(RoleDto roleDto) throws InvalidInputException, CustomException {
		//try {
			if(roleDto.getId() == null) {
				try {
					Role role = new Role();
					role = dtoToEntityAssembler(roleDto, role);
					roleRepo.save(role);
					roleDto.setId(role.getId());
					
					logger.info("done>>>>>>>>>>>>");
					}
				catch (Exception e) {
					throw new CustomException(400,"this role already exists");
				}
			}
			else {
				throw new InvalidInputException(400,"you are not allowed to enter id");
			}
			/*} catch (Exception e) {
			throw new InvalidInputException("not allowed");
		}*/
		return roleDto;
	}
	
	/*display all values*/
	
	public List<RoleDto> getAll() {
		List<Role> roleList = roleRepo.findAll();
		List<RoleDto> roleDtoList = new ArrayList<>();
		
		for(Role role : roleList) {
			RoleDto roleDto = new RoleDto();
			roleDto = entityToDtoAssembler(roleDto, role);
			roleDtoList.add(roleDto);
		}
		return roleDtoList;
	}
	
	/* displaying value by id */

	public RoleDto getById(Integer id) {
		Role role = roleRepo.findById(id).get();
		RoleDto roleDto = new RoleDto();
		roleDto = entityToDtoAssembler(roleDto, role);
		return roleDto;
	}
	
	/* updating value by id */
	
	public RoleDto update(RoleDto roleDto) throws InvalidInputException {
		try {
			Role role = new Role();
			Integer id = roleDto.getId();
			role = roleRepo.findById(id).get();
			
			role = dtoToEntityAssembler(roleDto, role);
			roleRepo.save(role);
		} catch (Exception e) {
			throw new InvalidInputException(e.toString());
		}
		return roleDto;
	}
	
	/*delete value by id*/
	
	public String delete(Integer roleId) {
		roleRepo.deleteById(roleId);
		return "role deleted";
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
