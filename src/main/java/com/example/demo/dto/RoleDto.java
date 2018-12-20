package com.example.demo.dto;

import javax.validation.constraints.NotNull;

public class RoleDto {
	@NotNull
	private Integer id;
	@NotNull
	private String name;
	
	public RoleDto() {
		super();
	}

	public RoleDto(@NotNull Integer id, @NotNull String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "RoleDto [id=" + id + ", name=" + name + "]";
	}
}
