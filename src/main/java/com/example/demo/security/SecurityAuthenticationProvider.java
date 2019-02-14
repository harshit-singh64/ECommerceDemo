package com.example.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.exception.CustomException;
import com.example.demo.jwt.JwtTokenDecoder;
import com.example.demo.jwt.JwtTokenValidator;

@Component
public class SecurityAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private SecurityValidator validator;
	@Autowired
	private JwtTokenValidator jwtTokenValidatorUtil;
	@Autowired
	private JwtTokenDecoder tokenDecoderUtil;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails user, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
	}
	
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
			throws AuthenticationException {
		
		
			SecurityAuthenticationToken securityAuthenticationToken = (SecurityAuthenticationToken) usernamePasswordAuthenticationToken;
			String token = securityAuthenticationToken.getToken();
			
			UserDetails userDetails = null;
			JwtUser jwtUser = null;
			String usernameFromToken = null;
			
			//System.out.println("token======="+token);
			
			try {
				usernameFromToken = tokenDecoderUtil.tokenDecoder(token);
				//System.out.println(usernameFromToken);
				userDetails = customUserDetailsService.loadUserByUsername(usernameFromToken);
				
				if (jwtTokenValidatorUtil.validateToken(token, userDetails)) {
					jwtUser = validator.validate(token);
					
					if(jwtUser != null) {
						List<GrantedAuthority> grantedAuthorities= AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
						
						UsernamePasswordAuthenticationToken authentication = jwtTokenValidatorUtil.getAuthentication(token, SecurityContextHolder.getContext().getAuthentication(), userDetails);
						System.out.println("authentication >>>>>>> "+authentication.toString()+" getPrincipal >>> "+authentication.getPrincipal());
						
						//authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						//System.out.println("request>>>>>>>>>"+request);
						
						logger.info("authenticated user " + username + ", setting security context");
						
						SecurityContextHolder.getContext().setAuthentication(authentication);
						//System.out.println("-----"+SecurityContextHolder.getContext().toString());
						//System.out.println("authentication>>>>>>>>>"+authentication);
						
						return new SecurityUserDetails(jwtUser.getUserName(),jwtUser.getId(),token,grantedAuthorities);
						}
					else {
						throw new CustomException("error in decoding");
						}
					}
				else {
					throw new CustomException("error in decoding");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("error in getting username form token>>>>>"+usernameFromToken);
					//request.setAttribute("expired", ex.getMessage());
					//throw new CustomException();
					}
			return userDetails;
			}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return SecurityAuthenticationToken.class.isAssignableFrom(aClass);
		}
	}
