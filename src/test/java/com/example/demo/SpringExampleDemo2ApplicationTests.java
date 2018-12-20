package com.example.demo;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.controller.UserController;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = UserController.class, secure = false)
public class SpringExampleDemo2ApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	
	@Test
	public void contextLoads() throws Exception {
		UserDto userDto = new UserDto(1,"anil","anil@gmail.com", "1236547896", "anil123", null);
		
		Mockito.when(userService.displayAllUsers()).thenReturn((List<UserDto>) userDto);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		System.out.println(mvcResult.getResponse());
		
		Mockito.verify(userService).displayAllUsers();
	}

}
