/*package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

public class SecurityAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
	public SecurityAuthenticationTokenFilter()
	{
		super("/api/**");
	}
	//decode token here
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {
		
		String authenticationToken=getToken(httpServletRequest);
		SecurityAuthenticationToken token = new SecurityAuthenticationToken(authenticationToken);
		return getAuthenticationManager().authenticate(token);
	}

	public String getToken(HttpServletRequest httpServletRequest) {
		String header = httpServletRequest.getHeader("Authorization");


        if (header == null) {
            throw new RuntimeException("JWT Token is missing");
        }

        String authenticationToken = header;
        return authenticationToken;
	}
	
	@Override
	 protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
	        super.successfulAuthentication(request, response, chain, authResult);
	        chain.doFilter(request, response);
	    }
	 
	public SecurityAuthenticationTokenFilter() {
		super();
		}
	
	@Value("${spring.security.signing-key}")
	private String secretKey  = "MaYzkSjmkzPC57L";
	
	public Authentication getAuthentication(HttpServletRequest request) {
		System.out.println("secretKey >>>>> "+secretKey);
		System.out.println("4>>>>>>>>>>>>>");
		
        String token = request.getHeader("Authorization");
        System.out.println("token from token filter "+token);
        if (token != null) {
            // parse the token.
            String username = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            System.out.println("username from token filter "+username);
            if (username != null) {
                return new AuthenticatedUser(username);
            }
        }
        return null;
    }
}
//we managed to retrieve a user*/