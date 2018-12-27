package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	public UserDto insertUser(@RequestBody @Valid UserDto userDto) throws InvalidInputException, CustomException {
		return userService.insertUser(userDto);
	}
	
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getAllStudents() {
		return userService.displayAllUsers();
	}
	
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto getStudentById(@PathVariable(value = "id") @Valid Integer userId) {
		return userService.displayById(userId);
	}
	
	@PutMapping("/user")
	public UserDto updateUser(@RequestBody @Valid UserDto userDto) throws InvalidInputException {
		return userService.updateUser(userDto);
	}
	
	@DeleteMapping("/user/{id}")
	public String delete(@PathVariable(value = "id") Integer userId) {
		userService.delete(userId);
		return "deleted";
	}
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
