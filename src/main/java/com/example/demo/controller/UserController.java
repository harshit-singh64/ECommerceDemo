package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.jwt.JwtTokenValidator;
import com.example.demo.login.Login;
import com.example.demo.security.SecurityUserDetails;
import com.example.demo.service.EmailService;
import com.example.demo.service.IUserService;
import com.example.demo.util.UtilResponse;

import redis.clients.jedis.Jedis;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private Login loginClass;
	@Autowired
	private JwtTokenValidator jwtTokenValidator;
	
	private Jedis jedis = new Jedis("127.0.0.1", 6379);
	
	@GetMapping("/userActive/{id}")
	public String activeUser(@PathVariable(value = "id") Integer userId) {
		emailService.activation(userId);
		return "Your Account is Activated !!!";
		}
	
	/*@PostMapping("/signup")
	public UserDto insertUser(@RequestBody @Valid UserDto userDto) throws InvalidInputException, CustomException, 
	UnsupportedEncodingException {
		return userService.insertUser(userDto);
		}*/
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getAll() throws CustomException {
		System.out.println("display : "+userService.displayAllUsers());
		return userService.displayAllUsers();
		}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable(value = "id") @Valid Integer userId,
			HttpServletRequest httpServletRequest) throws CustomException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityUserDetails securityUserDetails = (SecurityUserDetails) auth.getPrincipal();
		
		String name = auth.getAuthorities().toString();
		
		/*System.out.println("userDto====="+securityUserDetails.getId());
		System.out.println("name====="+name);
		System.out.println("SecurityContextHolder name====="+auth);
		System.out.println((userId.equals(securityUserDetails.getId()) && name.equals("[USER]")) +">>>>>"+ name.equals("[ADMIN]"));
		System.out.println(userId+"....."+"?????"+userId.equals(securityUserDetails.getId()));*/
		
		if((userId.equals(securityUserDetails.getId()) && name.equals("[USER]")) || (name.equals("[ADMIN]"))) {
			return new ResponseEntity<>(userService.displayById(userId), HttpStatus.OK);
		}
		else {
			UtilResponse utilResponse = new UtilResponse();
			utilResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
			utilResponse.setMessage("you are not allowed in this area");
			return new ResponseEntity<>(utilResponse, HttpStatus.OK);
			}
		}
	
	/*@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAll() throws CustomException {
		System.out.println("1>>>>>>>>>>>>>");
//		System.out.println("display : "+userService.displayAllUsers());
		return "hello";
		}*/
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll(HttpServletRequest httpServletRequest) throws CustomException {
		try {
			String token = httpServletRequest.getHeader("Authorization");
			System.out.println("token " + token);
			String username = jedis.get(token);
			System.out.println("username " + username);
			
			if(username != null) {
				List<UserDto> userDtoList = new ArrayList<>();
				//userDto = jwtTokenDecoder.tokenDecoder(token);
				userDtoList = jwtTokenValidator.tokenValidator(token);
				
				System.out.println("validated and decoded token : " + userDtoList);
				
				ArrayList roleNameList = (ArrayList) userDtoList.get(0).getRoleDto();
				LinkedHashMap<Object, Object> roleNameMap = (LinkedHashMap<Object, Object>) roleNameList.get(0);
				//System.out.println(roleNameMap.get("name")+"==============name");
				
				if(roleNameMap.get("name").equals("Admin")) {
					HttpHeaders responseHeader = new HttpHeaders();
					return new ResponseEntity<>(userService.displayAllUsers(), responseHeader, HttpStatus.OK);
					}
				else {
					throw new CustomException(400,"Invalid Access","you are not allowed in this area");
					}
				}
			else {
				throw new CustomException(400,"Invalid Input","Invalid token input");
				}
			}
		catch (NullPointerException e) {
			//e.printStackTrace();
			throw e;
			}
		catch (Exception e) {
			//e.printStackTrace();
			throw e;
			}
		}*/
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStudentById(@PathVariable(value = "id") @Valid Integer userId, 
			HttpServletRequest httpServletRequest) throws CustomException {
		try {
			String token = httpServletRequest.getHeader("Authorization");
			System.out.println("token " + token);
			String username = jedis.get(token);
			System.out.println("username " + username);
			//String username = jedis.get(token);
			
			if(username != null) {
				List<UserDto> userDtoList = new ArrayList<>();
				//userDto = jwtTokenDecoder.tokenDecoder(token);
				userDtoList = jwtTokenValidator.tokenValidator(token);
				
				System.out.println("validated and decoded token : " + userDtoList);
				
				ArrayList roleNameList = (ArrayList) userDtoList.get(0).getRoleDto();
				LinkedHashMap<Object, Object> roleNameMap = (LinkedHashMap<Object, Object>) roleNameList.get(0);
				//System.out.println(roleNameMap.get("name")+"==============name");
				UserDto userDtoFromDatabase = new UserDto();
				
				userDtoFromDatabase = userService.displayById(userId);
				
				System.out.println("dto from datbase " + userDtoFromDatabase);
				
				System.out.println(roleNameMap.get("name").equals("User"));
				System.out.println(userDtoFromDatabase.getEmail());
				System.out.println(username);
				
				//System.out.println(userDtoFromDatabase.getId().equals(userDto.getId()));
				
				
				if(roleNameMap.get("name").equals("Admin")) {
					HttpHeaders responseHeader = new HttpHeaders();
					return new ResponseEntity<>(userService.displayById(userId), responseHeader, HttpStatus.OK);
					}
				else if(roleNameMap.get("name").equals("User") &&
						(username.equals(userDtoFromDatabase.getEmail()) &&
						userDtoFromDatabase.getId().equals(userDtoFromDatabase.getId()))) {
					HttpHeaders responseHeader = new HttpHeaders();
					return new ResponseEntity<>(userService.displayById(userId), responseHeader, HttpStatus.OK);
				}
				else {
					throw new CustomException(400,"Invalid Access","you are not allowed in this area");
					}
				}
			else {
				throw new CustomException(400,"Invalid Input","Invalid token input");
				}
			}
		catch (NullPointerException e) {
			//e.printStackTrace();
			throw e;
			}
		catch (Exception e) {
			//e.printStackTrace();
			throw e;
			}
		
		String token = httpServletRequest.getHeader("Authorization");
		System.out.println("token " + token);
		String username = jedis.get(token);
		System.out.println("username " + username);
		
		UserDto userDto = new UserDto();
		Boolean loginSuccess = loginClass.login(userName, password,userId);
		
		if (loginSuccess == true) {
			userDto=userService.displayById(userId);
		}
		else{
			throw new CustomException("Login Error");
		}
		return userDto;*/
		//return userService.displayById(userId);
	/*}
	
	@PutMapping("/user")
	public UserDto updateUser(@RequestBody @Valid UserDto userDto,
			@RequestHeader(value = "userName") String userName,
			@RequestHeader(value = "password") String password) throws InvalidInputException, CustomException {
		
		Integer id = userDto.getId();
		Boolean loginSuccess = loginClass.login(userName, password,id);
		if (loginSuccess == true) {
			userDto = userService.updateUser(userDto);
			}
		else {
			throw new CustomException("Login Error");
			}
		return userDto;
		//return userService.updateUser(userDto);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer userId, 
			HttpServletRequest httpServletRequest, CustomException exception) throws CustomException {
		
		UtilResponse utilResponse = new UtilResponse();
		
		String token = httpServletRequest.getHeader("Authorization");
		System.out.println("token " + token);
		String username = jedis.get(token);
		System.out.println("username " + username);
		
		try {
			if(username != null) {
				List<UserDto> userDtoList = new ArrayList<>();
				//userDto = jwtTokenDecoder.tokenDecoder(token);
				userDtoList = jwtTokenValidator.tokenValidator(token);
				
				System.out.println("validated and decoded token : " + userDtoList);
				
				ArrayList roleNameList = (ArrayList) userDtoList.get(0).getRoleDto();
				LinkedHashMap<Object, Object> roleNameMap = (LinkedHashMap<Object, Object>) roleNameList.get(0);
				//System.out.println(roleNameMap.get("name")+"==============name");
				
				if(roleNameMap.get("name").equals("Admin")) {
					HttpHeaders responseHeader = new HttpHeaders();
					
					System.out.println(userId);
					String s = userService.delete(userId);
					System.out.println(s);
					
					utilResponse.setStatus(HttpStatus.CREATED.toString());
					utilResponse.setMessage("User with id "+ userId +" is Deleted!");
					
					return new ResponseEntity<>(utilResponse, responseHeader, HttpStatus.OK);
					}
				else {
					throw new CustomException(400,"Invalid Access","you are not allowed in this area");
					}
				}
			else {
				throw new CustomException(400,"Invalid Input","Invalid token input");
				}
			} catch (Exception e) {
				throw e;
				}
		}*/
	
	/*@PostMapping("/user")
	public User createStudent(@RequestBody UserDto user) {
	    return userService.insertStudent(user);
	}
	*/
	/*@Autowired
	IStudentRepo studentrepository;
	
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return studentrepository.findAll();
	}
	
	@GetMapping("/students/{id}")
	public Optional<Student> getStudentById(@PathVariable(value = "id") Integer studentId) {
		return studentrepository.findById(studentId);
	}
	
	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		Student savedStudent = studentrepository.save(student);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getStudentId()).toUri();

		return ResponseEntity.created(location).build();
	}
	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student) {
	    return studentrepository.save(student);
	}
	
	@DeleteMapping("/students/{id}")
	public void deleteStudent(@PathVariable(value = "id") Integer studentId) {
		studentrepository.deleteById(studentId);
	}
	
	@PutMapping("/students/{id}")
	public Student updateStudent(@PathVariable(value = "id") Integer studentId,@RequestBody Student studentDetails) {
		Optional<Student> student = studentrepository.findById(studentId);
		
		studentDetails.setStudentName(studentDetails.getStudentName());
		studentDetails.setStudentEmail(studentDetails.getStudentEmail());
		studentDetails.setStudentPasscode(studentDetails.getStudentPasscode());
		studentDetails.setStudentId(studentId);
		
		Student updateStudent = studentrepository.save(studentDetails);
		
		return updateStudent;
	}*/
}
