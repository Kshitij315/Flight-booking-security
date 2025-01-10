package com.bookmyflight.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyflight.security.dto.AuthenticationRequest;
import com.bookmyflight.security.dto.AuthenticationResponse;
import com.bookmyflight.security.dto.RegisterRequest;
import com.bookmyflight.security.model.Users;
import com.bookmyflight.security.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
		@Autowired
		private AuthenticationService authService;
	
	
		
		
			@PostMapping("/register")
			public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
					try {
						AuthenticationResponse response = authService.register(registerRequest);
						return ResponseEntity.ok(response);
					} catch (Exception e) {
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
					}
			}
			
			@PostMapping("/login")
		    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
		        try {
		            AuthenticationResponse res = authService.authenticate(authenticationRequest);
		            return ResponseEntity.ok(res);
		        } catch (Exception e) {
		            System.out.println(e.toString());
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		        }

		    }
			
			
//	
//	@PostMapping("/register")
//	public Users registerUser(@RequestBody Users user) {
//        return authService.register(user);
//	}

	
//	@PostMapping("/login")
//	public String loginUser(@RequestBody Users user) {
//	    return service.verify(user);
//	}
	
//	@PostMapping("/login")
//	public String loginUser(@RequestBody Users user) {
//		return authService.authenticate(user);
//	}
	
	
}
