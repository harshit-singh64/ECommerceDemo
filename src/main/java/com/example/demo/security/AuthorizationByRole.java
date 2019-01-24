package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.repo.IUserRepo;

@Component
public class AuthorizationByRole {
	@Autowired
	private IUserRepo userRepo;
	
	public boolean authorizationOfUser(String email) {
		User user = userRepo.findByEmail(email);
		System.out.println(user);
		System.out.println(user.getRole().get(0).getName());
		if(user.getRole().get(0).getName().equals("vkkgeneral")) {
			return true;
		}
		return false;
	}
}
