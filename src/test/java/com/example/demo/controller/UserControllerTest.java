package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.util.EmailSender;
import com.example.demo.util.Login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = UserController.class, secure = false)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private EmailSender emailSender;
	@MockBean
	private Login login;
	
	/*@Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
    }*/
	
	/*inserting*/
	
	@Test
	public void create() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anil");
		user.setEmail("anil@gmail.com");
		user.setPassword("sdf12");
		user.setPhoneNumber("7896541236");
		user.setRoleDto(null);
		
		String inputInJson = this.mapToJson(user);
		
		Mockito.when(userService.insertUser(Mockito.any(UserDto.class))).thenReturn(user);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	@Test
	public void create2() throws Exception {
		UserDto user = new UserDto();
		user.setId(null);
		user.setName(null);
		user.setEmail(null);
		user.setPassword(null);
		user.setPhoneNumber(null);
		user.setRoleDto(null);
		
		String inputInJson = this.mapToJson(user);
		
		Mockito.when(userService.insertUser(Mockito.any(UserDto.class))).thenReturn(user);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	@Test
	public void create3() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anilghjmnfghmnhyjmhyjmhymghjmgjmgyj");
		user.setEmail("sdfghfgbn@");
		user.setPassword("sdf12");
		user.setPhoneNumber("gfjgfhjkyjukyjju");
		user.setRoleDto(null);
		
		String inputInJson = this.mapToJson(user);
		
		Mockito.when(userService.insertUser(Mockito.any(UserDto.class))).thenReturn(user);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	@Test
	public void create4() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anil");
		user.setEmail("g@gmail.com");
		user.setPassword("sdfghjfghjff12");
		user.setPhoneNumber("ghj67");
		user.setRoleDto(null);
		
		String inputInJson = this.mapToJson(user);
		
		Mockito.when(userService.insertUser(Mockito.any(UserDto.class))).thenReturn(user);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	/*display*/
	
	@Test
	public void getAll() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anil");
		user.setEmail("aghjfghfhfnil@gmail.com");
		user.setPassword("sdf12");
		user.setPhoneNumber("ghj67");
		user.setRoleDto(null);
		
		List<UserDto> userList = new ArrayList<>();
		userList.add(user);
		
		String expectedJson = this.mapToJson(userList);
		
		Mockito.when(userService.displayAllUsers()).thenReturn(userList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/user")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	/*display by id*/
	
	@Test
	public void getById() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anil");
		user.setEmail("anil@gmail.com");
		user.setPassword("sdf12");
		user.setPhoneNumber("7896541236");
		user.setRoleDto(null);
		
		Mockito.when(userService.displayById(Mockito.anyInt())).thenReturn(user);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/user/1")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String expectedJson = this.mapToJson(user);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	@Test
	public void getById2() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anil");
		user.setEmail("anil@gmail.com");
		user.setPassword("sdf12");
		user.setPhoneNumber("7896541236");
		user.setRoleDto(null);
		
		Mockito.when(userService.displayById(Mockito.anyInt())).thenReturn(user);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/user/a")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String expectedJson = this.mapToJson(user);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	@Test
	public void getById3() throws Exception {
		UserDto user = new UserDto();
		user.setId(1);
		user.setName("anil");
		user.setEmail("anil@gmail.com");
		user.setPassword("sdf12");
		user.setPhoneNumber("7896541236");
		user.setRoleDto(null);
		
		Mockito.when(userService.displayById(Mockito.anyInt())).thenReturn(user);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/user")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String expectedJson = this.mapToJson(user);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
