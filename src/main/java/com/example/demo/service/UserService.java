package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.repo.IUserRepo;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String emailFrom;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
	
	public UserDto entityToDtoAssembler(UserDto userDto, User user) {
		
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		userDto.setPhoneNumber(user.getPhoneNumber());

		return userDto;
	}
	
	public User dtoToEntityAssembler(UserDto userDto, User user) {
		
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setPhoneNumber(userDto.getPhoneNumber());

		return user;
	}
	
	/*account activation*/
	
	public void activation(Integer id) {
		User user = userRepo.findById(id).get();
		//user.setStatus((byte) 1);
		userRepo.save(user);
	}
	
	/*sending email*/
	
	public void sendMail(String userName, String password, String name, Integer id) throws UnsupportedEncodingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			message.setFrom(new InternetAddress());
			message.setContent("<h1>Registeration Successfull !</h1>"

					+ "YOUR ACCOUNT IS READY<br><br><br>Hello " + name + "   ,<br><br>"
					+ "Thank You for registering in E-Commerce where you can spread your"
					+ " buissness in every corner of the country. Below are your" + " credentials for login."
					+ "<br><br>        Username is :   " + userName + "<br><br>        Password is :   " + password
					+ "<br><br><body><a href=http://localhost:8081/api/activateAccount/"+id+">Click here to Activate Your Account</a></body>", "text/html");
			helper.setTo(userName);
			helper.setFrom(new InternetAddress(emailFrom,"E-Commerce"));
			helper.setSubject("E-Commerce Registration");

		} catch (MessagingException e) {
			e.printStackTrace();
			//return "Error while sending mail ..";
			System.out.println("Error while sending mail ..");
		}
		mailSender.send(message);
		System.out.println("Mail Sent Success!");
		//System.out.println("Mail Sent Success!");
		//return "Mail Sent Success!";
	}
	
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
	
	
	/*inserting value*/
	
	public UserDto insertUser(UserDto userDto) throws InvalidInputException, CustomException {
		//try {
		User user = new User();
			if(userDto.getId() == null || userDto.getPassword() == null) {
					try {
						userDto.setPassword(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
						user = dtoToEntityAssembler(userDto, user);
						
						userRepo.save(user);
						userDto.setId(user.getId());
						sendMail(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getId());
						logger.info("done>>>>>>>>>>>>");
						//System.out.println("Mail Sent Success!");
					} catch (Exception e) {
						throw new InvalidInputException(400,"contact number and email id must be unique");
					}
				/*try {
					User user = new User();
					userDto.setPassword(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
					user = dtoToEntityAssembler(userDto, user);
					userRepo.save(user);
					userDto.setId(user.getId());
					
					logger.info("done>>>>>>>>>>>>");
					sendMail(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getId());
					} catch (Exception e) {
					throw new CustomException(400,"this contact number and email already exists");
					}*/
				}
			else {
				throw new InvalidInputException(400,"you are not allowed to enter id and password");
			}
			/*} catch (Exception e) {
			throw new InvalidInputException("not allowed");
		}*/
		return userDto;
	}
	
	/*display all values*/
	
	public List<UserDto> displayAllUsers() {
		
		List<User> userList = userRepo.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		
		for(User user: userList) {
			UserDto userDto = new UserDto();
			userDto = entityToDtoAssembler(userDto, user);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}
	
	/* displaying value by id */

	public UserDto displayById(Integer id) {
		User user = userRepo.findById(id).get();
		UserDto userDto = new UserDto();
		userDto = entityToDtoAssembler(userDto, user);
		return userDto;
	}

	/* updating value by id */
	
	public UserDto updateUser(UserDto userDto) throws InvalidInputException {
		try {
			User user = new User();
			Integer id = userDto.getId();
			user = userRepo.findById(id).get();
			System.out.println(user);
			user = dtoToEntityAssembler(userDto, user);
			userRepo.save(user);
			//userDto.setId(user.getId());
		} catch (Exception e) {
			throw new InvalidInputException(e.toString());
		}
		return userDto;
	}
	
	/*delete value by id*/
	
	public String delete(Integer userId) {
		userRepo.deleteById(userId);
		return "user deleted";
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

