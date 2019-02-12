package com.example.demo.security;

import java.util.List;

import com.example.demo.dto.RoleDto;

public class JwtUser {
	private String userName;
    private Integer id;
    private String role;
    
    private List<RoleDto> roleDtoList;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<RoleDto> getRoleDtoList() {
		return roleDtoList;
	}
	public void setRoleDtoList(List<RoleDto> roleDtoList) {
		this.roleDtoList = roleDtoList;
	}
	
}
