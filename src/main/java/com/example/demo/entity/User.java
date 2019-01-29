package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String phoneNumber;
	private String password;
	private String assignedRole;
	
	@ManyToMany(fetch=FetchType.EAGER)
	List<Role> role = new ArrayList<Role>();
	
	public User() {
		super();
	}
	
	public User(Integer id, String email, String password, String assignedRole) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.assignedRole = assignedRole;
	}

	public User(Integer id, String name, String email, String phoneNumber, String password, List<Role> role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAssignedRole() {
		return assignedRole;
	}



	public void setAssignedRole(String assignedRole) {
		this.assignedRole = assignedRole;
	}



	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", password="
				+ password + ", role=" + role + "]";
	}
	
	
}
