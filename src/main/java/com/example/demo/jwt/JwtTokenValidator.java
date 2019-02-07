package com.example.demo.jwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidator implements Serializable {
	@Value("${spring.security.signing-key}")
	private String secretKey = "MaYzkSjmkzPC57L";
	
	@Autowired
	private JwtTokenDecoder jwtTokenDecoder;
	
	public String getUsernameFromToken(String token) {
		System.out.println();
        return getClaimFromToken(token, Claims::getSubject);
    }
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        System.out.println("claims >>>" +claims);
        return claimsResolver.apply(claims);
    }
	
	private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
	
	public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
	
	private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }
	 
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		System.out.println(token+" >>>>>>> "+ userDetails);
        final String username = getUsernameFromToken(token);
        System.out.println("username from token >>>>>>> "+ username);
        
        return ( username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	public UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {
		
		System.out.println("token>>>>>"+token+" >>>existingAuth>>>>"+existingAuth+">>>userDetails>>>"+userDetails);
        final JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);
        
        System.out.println("jwtParser>>>>>>> "+jwtParser);
        
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();
        
        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        
        System.out.println(userDetails+ " >>>>> ");
        
        return new UsernamePasswordAuthenticationToken(userDetails, " ", authorities);
    }
	/*public List<UserDto> tokenValidator(String token) throws CustomException {
		try {
			System.out.println("token from tokenValidator : "+token);
			
			HashMap<UserDto, Date> tokenDecodedMap = new HashMap<>();
			//tokenDecodedMap = jwtTokenDecoder.tokenDecoder(token);
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
		}*/
	}
