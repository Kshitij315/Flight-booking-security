package com.bookmyflight.security.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookmyflight.security.dto.AuthenticationRequest;
import com.bookmyflight.security.dto.AuthenticationResponse;
import com.bookmyflight.security.dto.RegisterRequest;
import com.bookmyflight.security.model.Role;
import com.bookmyflight.security.model.UserPrinciple;
import com.bookmyflight.security.model.Users;
import com.bookmyflight.security.repo.UserRepo;

@Service
public class AuthenticationService  {

	@Autowired
	private UserRepo userRepository;
	
	
	
	
	@Autowired
	private JwtService jwtservice;
	
	@Autowired
	private AuthenticationManager autheManager;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	
//	public Users register(Users user) {
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		return repo.save(user);
//	}
	
	public AuthenticationResponse register(RegisterRequest registerRequest) {
		 var user = new Users();
		    user.setUsername(registerRequest.getUsername());
		    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		    user.setRole(Role.USER);
		    
		    userRepository.save(user);
		    var jwtToken=jwtservice.generateToken(user.getUsername(), user.getRole());
		    return new AuthenticationResponse(jwtToken);
		
		
    }
	
	
	 public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
	        autheManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
	        );
	        Users user = userRepository.findByUsername(authenticationRequest.getUsername());
	        String jwtToken = jwtservice.generateToken(user.getUsername(),user.getRole());
	        System.out.println("Role is"+user.getRole());
	        return new  AuthenticationResponse(jwtToken);
	    }
	 
	 
	 


//	public String verify(Users user) {
//		Authentication authentication = 
//				autheManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//		
//		
//		if(authentication.isAuthenticated()) {
//			return jwtservice.generateToken(user.getUsername(), user.getRole());
//		}
//		else {
//			return "Failed";
//		}
//		
	
	public String authenticate(Users user) {
		Authentication authentication = autheManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtservice.generateToken(user.getUsername(), user.getRole());
		} else {
			 throw new IllegalArgumentException("Invalid username or password");
		}
		
	}
}
	
