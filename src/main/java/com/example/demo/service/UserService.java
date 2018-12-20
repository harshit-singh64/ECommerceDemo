package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.repo.IUserRepo;

@Service
public class UserService {
	@Autowired
	private IUserRepo userRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
	
	public UserDto EntityToDtoAssembler(User user) {
		
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		userDto.setPhoneNumber(user.getPhoneNumber());

		return userDto;
	}
	
	public User dtoToEntityAssembler(UserDto userDto) {

		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setPhoneNumber(userDto.getPhoneNumber());

		return user;
	}
	
	public UserDto insertUser(UserDto userDto) throws InvalidInputException, CustomException {
		//try {
			if(userDto.getId() == null) {
				try {
				User user = new User();
				user = dtoToEntityAssembler(userDto);
				userRepo.save(user);
				userDto.setId(user.getId());
				
				logger.info("done>>>>>>>>>>>>");
				}
				catch (Exception e) {
					throw new CustomException(404,"contact number and email id must be unique");
				}
			}
			else {
				throw new InvalidInputException(404,"you are not allowed to enter ids");
			}
			/*} catch (Exception e) {
			throw new InvalidInputException("not allowed");
		}*/
		return userDto;
	}
	
	public List<UserDto> displayAllUsers() {
		
		List<User> userList = userRepo.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		
		for(User user: userList) {
			UserDto userDto;
			userDto = EntityToDtoAssembler(user);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}
	
	public UserDto updateUser(@RequestBody @Valid UserDto userDto) throws InvalidInputException {
		try {
			User user = new User();
			
			Integer id = userDto.getId();
			
			user = userRepo.findById(id).get();
			
			System.out.println(user);
			
			user = dtoToEntityAssembler(userDto);
			userRepo.save(user);
			//userDto.setId(user.getId());
		} catch (Exception e) {
			throw new InvalidInputException(e.toString());
		}
		return userDto;
	}
	
	/*public User insertStudent(@RequestBody UserDto userDto) {
		
		User user = new User();
		
		user.setPassword(UUID.randomUUID().toString().substring(0,3));//for random string
		 
		 User checkingUserByEmail = userRepo.findByUserEmail(user.getUserEmail());
		 if(checkingUserByEmail != null) {
			 throw new UserNotFoundException("User already exist with given email");
		 }
		 
		 @SuppressWarnings("unchecked")
		 Set<Role> role = roleService.
		 
		 //System.out.println(role);
		 
		 //Set<Role> roles = new HashSet<>();
		 roles.addAll(role);
		 user.setRole(roles);
		 
		 
		 user.setId(userDto.getId());
		 user.setName(userDto.getName());
		 user.setEmail(userDto.getEmail());
		 user.setPassword(UUID.randomUUID().toString().substring(0,3));
		 //user.setPassword(userDto.getPassword());
		 
		 List<Role> roles = new ArrayList<>();
		 
		 //id = userDto.getRole();
		 
		 for (int i = 0; i < userDto.getRole().size(); i++) {
			 Integer roleID = userDto.getRole().get(i);
			 Role role = roleRepo.findById(roleID).get();
			 roles.add(role);
			 
			 System.out.println(roleID+"id");
			 System.out.println(roles+"aaaa");
			 System.out.println(user+"bbbb");
			 
			 }
		 user.setRole(roles);
		 
		 
		 return userRepo.save(user);
	    
	}*/
}

