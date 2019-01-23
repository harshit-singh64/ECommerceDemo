package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repo.IUserRepo;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private UserService userService;
	
	public UserDto login(String username, String password) {
		User user = userRepo.findByEmailAndPassword(username, password);
		UserDto userDto = new UserDto();
		userDto = userService.entityToDtoAssembler(userDto, user);
		return userDto;
	}
	
	/*public Boolean login(String userName, String password) throws InvalidInputException {
		Boolean loginSuccess = false;
		try {
			User user = userRepo.findByEmail(userName).get();
			String existingEmail = user.getEmail();
			
			if(!existingEmail.equals(null)) {
				System.out.println(user);
				if (userName.equals(user.getEmail()) && password.equals(user.getPassword())) {
					loginSuccess = true;
					} else {
						throw new InvalidInputException(500,"Login not successfull");
						}
				} else {
					throw new InvalidInputException(400,"Login username does not exists");
				}
		} catch (Exception e) {
			throw new InvalidInputException(400,"Login ugggsername does not exists");
		}
		
		return loginSuccess;
		}*/
	
	/*@Cacheable("student")
	public String loginSucess(Integer id) {
		try {
			System.out.println("Going to sleep for 5 Secs.. to simulate backend call.");
			Thread.sleep(1000*5);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return "sucess";
	}*/
}