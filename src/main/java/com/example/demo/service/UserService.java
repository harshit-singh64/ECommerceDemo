package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.repo.IUserRepo;

@Service
public class UserService implements IUserService {
	/*@Autowired
	private BCryptPasswordEncoder encoder;*/
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private EmailService emailService;
	@Autowired
	private RoleService roleService;
	
	@Value("${spring.mail.username}")
	private String emailFrom;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
	
	public UserDto entityToDtoAssembler(UserDto userDto, User user) {
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRoleDto(entityToDtoListAssembler(userDto, user));

		return userDto;
	}
	
	public List<RoleDto> entityToDtoListAssembler(UserDto userDto, User user) {
		List<Role> roleList = user.getRole();
		List<RoleDto> roleDtoList = new ArrayList<>();
				
		for(Role role: roleList) {
			RoleDto roleDto = new RoleDto();
			roleDto = roleService.entityToDtoAssembler(roleDto, role);
			roleDtoList.add(roleDto);
		}
		return roleDtoList;
	}
	
	public User dtoToEntityAssembler(UserDto userDto, User user) {
		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setRole(dtoToEntityListAssembler(userDto, user));

		return user;
	}
	
	public List<Role> dtoToEntityListAssembler(UserDto userDto, User user) {
		List<RoleDto> roleDtoList = userDto.getRoleDto();
		List<Role> roleList = new ArrayList<>();
				
		for(RoleDto roleDto: roleDtoList) {
			Role role = new Role();
			role = roleService.dtoToEntityAssembler(roleDto, role);
			roleList.add(role);
		}
		return roleList;
	}
	
	
	/*inserting value*/
	
	@SuppressWarnings({"unchecked" })
	public UserDto insertUser(UserDto userDto) throws InvalidInputException {
		//try {
		User user = new User();
		//Role role = new Role();
			if(userDto.getId() == null && userDto.getPassword() == null && userDto.getRoleDto() == null) {
					try {
						userDto.setPassword(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
						RoleDto defaultRole = new RoleDto("User");
						//ArrayList defaultRole = new ArrayList();
						//ArrayList<RoleDto> defaultRoleList = new ArrayList<>();
						List<RoleDto> defaultRoleList = new ArrayList<>();
						defaultRoleList.add(defaultRole);
						//userDto.setRoleDto(defaultRoleList);
						
						userDto.setRoleDto(defaultRoleList);
						
						System.out.println(userDto);
						
						/*
						List<RoleDto> roleDtoList = new ArrayList<>();
						List<Role> roleList = new ArrayList<>();
						roleDtoList = userDto.getRoleDto();
						
						System.out.println(roleDtoList);
						
						for(RoleDto roleDto: roleDtoList) {
							Role role = new Role();
							role = roleService.dtoToEntityAssembler(roleDto, role);
							roleList.add(role);
						}
						
						System.out.println(roleList);*/
						
						System.out.println(user);
						
						
						
						
						user = dtoToEntityAssembler(userDto, user);
						
						System.out.println(user);
						
						userRepo.save(user);
						
						System.out.println(user);
						
						
						emailService.sendMail(user.getEmail(), user.getPassword(), user.getName(), user.getId());
						
						userDto.setId(user.getId());
						
						logger.info("done>>>>>>>>>>>>");
					} catch (Exception e) {
						//e.printStackTrace();
						throw new InvalidInputException(400,e.toString());
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
				throw new InvalidInputException(400,"you are not allowed to enter id and password and role");
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
		//List<Role> roleList 
				
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

	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// hard coding the users. All passwords must be encoded.
				final List<AppUser> users = Arrays.asList(
					new AppUser (1, "omar", encoder.encode("12345"), "USER"),
					new AppUser (2, "admin", encoder.encode("12345"), "ADMIN")
				);
				
				for(AppUser user: users) {
					if(user.getUsername().equals(username)) {
						
						// Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
						// So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
						List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				                	.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
						
						// The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
						// And used by auth manager to verify and check user authentication.
						return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
					}
				}
				
				// If user not found. Throw this exception.
				throw new UsernameNotFoundException("Username: " + username + " not found");
	
		//return null;
	}*/
	
	/*private static class AppUser {
		private Integer id;
	    	private String username, password;
	    	private String role;
	    
		public AppUser(Integer id, String username, String password, String role) {
	    		this.id = id;
	    		this.username = username;
	    		this.password = password;
	    		this.role = role;
	    	}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		
	}*/
	
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

