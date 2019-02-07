/*package com.example.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SecurityAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private String token;
	
	public SecurityAuthenticationToken(String token) {
		super(null, null);
		this.token=token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public Object getCredentials()
	{
		return null;
	}
	
	@Override
	public Object getPrincipal() {
		return null;
	}
}
*/