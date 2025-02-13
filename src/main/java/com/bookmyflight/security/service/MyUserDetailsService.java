package com.bookmyflight.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookmyflight.security.model.UserPrinciple;
import com.bookmyflight.security.model.Users;
import com.bookmyflight.security.repo.UserRepo;


@Service
public class MyUserDetailsService implements UserDetailsService

{
	@Autowired  
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user=userRepo.findByUsername(username);
		if(user==null) {
            throw new UsernameNotFoundException("User not found");

	}
		
		
		return new UserPrinciple(user);
	
	
	}
}


