package com.example.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.repo.IUserRepo;

@Component
public class Login {
	@Autowired
	private IUserRepo userRepo;
	
	/*login*/
	
	public Boolean login(String userName, String password, Integer id) throws CustomException {
		Boolean loginSuccess = false;
		User user = userRepo.findById(id).get();
		String existingEmail = user.getEmail();
		if (!existingEmail.equals(userName)) {
			throw new InvalidInputException(400,"Login username does not exists");
		} else {
			if (userName.equals(user.getEmail()) && password.equals(user.getPassword())) {
				loginSuccess = true;
			} else {
				throw new InvalidInputException(500,"Login not successfull");
			}
		}
		return loginSuccess;
	}
}
