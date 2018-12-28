package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

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

import com.example.demo.dto.RoleDto;
import com.example.demo.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = RoleController.class, secure = false)
public class RoleControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private RoleService roleService;
	
	/*inserting*/
	
	@Test
	public void create() throws Exception {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1);
		roleDto.setName("Teacher");
		
		String inputInJson = this.mapToJson(roleDto);
		
		Mockito.when(roleService.insert(Mockito.any(RoleDto.class))).thenReturn(roleDto);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/role")
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
		RoleDto roleDto = new RoleDto();
		roleDto.setId(null);
		roleDto.setName(null);
		
		String inputInJson = this.mapToJson(roleDto);
		
		Mockito.when(roleService.insert(Mockito.any(RoleDto.class))).thenReturn(roleDto);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/role")
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
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1);
		roleDto.setName(null);
		
		String inputInJson = this.mapToJson(roleDto);
		
		Mockito.when(roleService.insert(Mockito.any(RoleDto.class))).thenReturn(roleDto);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/role")
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
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1);
		roleDto.setName("anil");
		
		List<RoleDto> roleDtoList = new ArrayList<>();
		roleDtoList.add(roleDto);
		
		Mockito.when(roleService.getAll()).thenReturn(roleDtoList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/role")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String expectedJson = this.mapToJson(roleDtoList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	/*display by id*/
	
	@Test
	public void getById() throws Exception {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1);
		roleDto.setName("anil");
		
		Mockito.when(roleService.getById(Mockito.anyInt())).thenReturn(roleDto);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/role/1")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String expectedJson = this.mapToJson(roleDto);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	@Test
	public void getById2() throws Exception {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1);
		roleDto.setName("anil");
		
		Mockito.when(roleService.getById(Mockito.anyInt())).thenReturn(roleDto);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/role/a")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String expectedJson = this.mapToJson(roleDto);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	@Test
	public void getById3() throws Exception {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1);
		roleDto.setName("anil");
		
		Mockito.when(roleService.getById(Mockito.anyInt())).thenReturn(roleDto);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/role/")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String expectedJson = this.mapToJson(roleDto);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
