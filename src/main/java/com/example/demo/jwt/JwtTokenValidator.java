package com.example.demo.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;

@Component
public class JwtTokenValidator {
	@Autowired
	private JwtTokenDecoder jwtTokenDecoder;
	
	public List<UserDto> tokenValidator(String token) throws CustomException {
		try {
			System.out.println("token from tokenValidator : "+token);
			
			HashMap<UserDto, Date> tokenDecodedMap = new HashMap<>();
			tokenDecodedMap = jwtTokenDecoder.tokenDecoder(token);
//			System.out.println("tokenDecodedMap : "+tokenDecodedMap);
//			System.out.println("tokenDecodedMap : "+tokenDecodedMap.keySet().toArray()[0]);
//			System.out.println("tokenDecodedMap : "+tokenDecodedMap.values().toArray()[0]);
			Date currentDateAndTime = new Date();
			Date expirationDateAndTime = (Date) tokenDecodedMap.values().toArray()[0];
			System.out.println("expirationDateAndTime : "+expirationDateAndTime);
			
			if(expirationDateAndTime.after(currentDateAndTime)) {
				Set<UserDto> userDtoSet = new HashSet<>();
				userDtoSet = tokenDecodedMap.keySet();
				System.out.println("userDtoSet : "+userDtoSet);
				
				int n = userDtoSet.size(); 
				System.out.println("size : " + n);
			    List<UserDto> userDtoList = new ArrayList<>(n);
			    for (UserDto userDto : userDtoSet) {
			    	userDtoList.add(userDto);
			    	}
				System.out.println("userdtolist : "+ userDtoList);
				
				return userDtoList;
			}
			else {
				throw new CustomException("Token Expired");
			}
			
			
		} catch (NullPointerException e) {
			//e.printStackTrace();
			throw new CustomException(500,"error in deocoding","null value");
			}
		catch (CustomException e) {
			//e.printStackTrace();
			throw new CustomException(500,"error in deocoding",e.toString());
			}
		catch (Exception e) {
			//e.printStackTrace();
			throw new CustomException(500,"error in deocoding",e.toString());
			}
		}
	}
