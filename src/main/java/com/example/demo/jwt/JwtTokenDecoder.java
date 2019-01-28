package com.example.demo.jwt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.service.IUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.bytebuddy.dynamic.scaffold.MethodGraph.Linked;

@Component
public class JwtTokenDecoder {
	@Autowired
	private IUserService userService;
	
	@Value("${spring.security.signing-key}")
	private String secretKey;
	
	//String secretKey = "my-secret-token-to-change-in-production";
	
	
	@SuppressWarnings({ "unchecked"})
	public UserDto tokenDecoder(String token) throws CustomException {
		UserDto userDto = new UserDto();
		User user = new User();
		try {
			System.out.println("token from tokenDecoder "+token);
			
			Claims claims = Jwts
					.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();
			
			userDto.setEmail(claims.getSubject());
			userDto.setId((Integer) claims.get("id"));
			userDto.setName((String) claims.get("name"));
			userDto.setPhoneNumber((String) claims.get("phoneNumber"));
			userDto.setRoleDto((List<RoleDto>) claims.get("role"));
			
			/*@SuppressWarnings("rawtypes")
			ArrayList roleNameList = (ArrayList) claims.get("role");
			LinkedHashMap<Object, Object> roleNameMap = (LinkedHashMap<Object, Object>) roleNameList.get(0);
			System.out.println(roleNameMap.get("name")+"==============name");*/
		} catch (Exception e) {
			//e.printStackTrace();
			throw new CustomException(500,e.toString()+"error in deocoding");
			}
		return userDto;
		}
	}
