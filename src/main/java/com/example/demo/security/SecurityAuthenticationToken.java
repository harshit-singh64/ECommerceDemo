package com.example.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SecurityAuthenticationToken extends UsernamePasswordAuthenticationToken{
	private static final long serialVersionUID = 2657215068781221886L;
	
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
