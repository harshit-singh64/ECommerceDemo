package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = UserController.class, secure = false)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	
	/*@Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
    }*/
	
	@Test
	public void getAll() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anil");
		user.setEmail("anil@gmail.com");
		user.setPassword("sdf12");
		user.setPhoneNumber("1236547891");
		user.setRoleDto(null);
		
		UserDto user2 = new UserDto();
		user2.setId(2);
		user2.setName("anil2");
		user2.setEmail("anil2@gmail.com");
		user2.setPassword("sdf12");
		user2.setPhoneNumber("1236547892");
		user2.setRoleDto(null);
		
		List<UserDto> userList = new ArrayList<>();
		userList.add(user);
		userList.add(user2);
		
		String expectedJson = this.mapToJson(userList);
		
		
		Mockito.when(userService.displayAllUsers()).thenReturn(userList);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
				 .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
		
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
