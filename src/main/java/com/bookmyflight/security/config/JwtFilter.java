package com.bookmyflight.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookmyflight.security.service.JwtService;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	private static final String BEARER_PREFIX = "Bearer ";
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ApplicationContext context;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWtlc2giLCJpYXQiOjE3MzYwOTI4MzYsImV4cCI6MTczNjA5Mjk0NH0.rCZ6lDpZpQoEiC2BRyrWs_wFSRkWQo61u92UVHqx1So
		
		String authorizationHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		
		try {
		if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
			token = authorizationHeader.substring(BEARER_PREFIX.length());
			userName = jwtService.extractUserName(token);
		}
		
		
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

			if (jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
	} catch (Exception e) {
		logger.error("Error during Jwt validation: ", e);
	}	
		
		filterChain.doFilter(request, response);
		
	}

}
