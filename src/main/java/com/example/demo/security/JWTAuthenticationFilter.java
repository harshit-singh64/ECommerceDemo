package com.example.demo.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.jwt.JwtTokenDecoder;
import com.example.demo.jwt.JwtTokenValidator;

public class JWTAuthenticationFilter extends OncePerRequestFilter  {
	@Autowired
	private JwtTokenDecoder tokenDecoderUtil;
	@Autowired
	private JwtTokenValidator jwtTokenValidatorUtil;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
        String username = null;
        String authToken = null;
        
        //UserDto userDto = new UserDto();
        
        if (header != null) {
        	authToken = header;
        	System.out.println("token from header : "+authToken);
        try {
        	username = tokenDecoderUtil.tokenDecoder(authToken);
        	System.out.println(username);
        	//userDto = tokenDecoderUtil.tokenDecoderDto(authToken);
        	//System.out.println(userDto);
        	//username = jwtTokenUtil.getUsernameFromToken(authToken);
        	} catch (Exception e) {
        			System.out.println("1>> "+e.toString());
        			logger.warn("Authentication Failed. Username or Password not valid.>>>>>>>the token is expired and not valid anymore", e);
        			}
        } else {
        	logger.warn("couldn't find bearer string, will ignore the header");
        	}
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	try {
        		System.out.println("userDetails >>>>>>>>>>"+username);
        		
        		//customUserDetailsService.loadUserByUsername(username);
        		
        		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        		
        		System.out.println("userDetails >>>>>>>>>>"+userDetails);
        		
        		if (jwtTokenValidatorUtil.validateToken(authToken, userDetails)) {
        			//System.out.println(">>>>>>>> "+jwtTokenValidatorUtil.validateToken(authToken, userDetails));
                    UsernamePasswordAuthenticationToken authentication = jwtTokenValidatorUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                   // UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    
                    System.out.println("authentication >>>>>>> "+authentication.toString()+" getPrincipal >>> "+authentication.getPrincipal());
                    System.out.println(" getCredentials >> "+authentication.getCredentials().toString());
                    
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    System.out.println("request>>>>>>>>>"+request);
                    
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    System.out.println(authentication.isAuthenticated());
                }
        		
            	} catch (Exception e) {
            			System.out.println("exception >>>>>>>>> "+e.toString());
            			}
        }
        filterChain.doFilter(request, response);
    }
}
        	//UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            
            /*System.out.println("userDetails >>>>>>>>>>"+userDetails);
            
            if (jwtTokenValidatorUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenValidatorUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }*/
       
/*package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	protected JWTAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
			System.out.println("3>>>>>>>>>>>>>");
		
			System.out.println("dofilters from authFilter"+request+">>>>>>>>"+response+">>>>>>>>"+filterChain);
		
		Authentication authentication = new SecurityAuthenticationTokenFilter().getAuthentication((HttpServletRequest) request);
        
			System.out.println("authentication from authFilter"+authentication.getName()+">>>>>>>>"+authentication.toString());
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}
}
*/