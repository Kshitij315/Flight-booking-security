package com.bookmyflight.security.dto;

public class AuthenticationResponse {
	
	
	private String authenticationToken;
	
	public AuthenticationResponse(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	
	
	

}
