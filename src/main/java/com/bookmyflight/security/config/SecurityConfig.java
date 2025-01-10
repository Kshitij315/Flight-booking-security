package com.bookmyflight.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bookmyflight.security.model.Role;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Autowired
	private JwtFilter jwtFilter;

	    @Autowired
	    private UserDetailsService userDetailsService;
	    
	   
		@Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
				return http.csrf(csrf -> csrf.disable())
		                .authorizeRequests(requests -> requests.antMatchers("/api/v1/auth/**").permitAll()
		                        .antMatchers("api/v1/admin/**").hasRole(Role.ADMIN.toString())
		                        .antMatchers("api/v1/user/**").hasAnyAuthority(Role.USER.toString(),Role.ADMIN.toString())// Use antMatchers instead of requestMatcher
		                        .anyRequest().authenticated())
		                .httpBasic(Customizer.withDefaults())
		                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		                .build();
						
		}
	
//
//		@Bean
//		public UserDetailsService userDetailsService() {
//			UserDetails adminUser = 
//					User.withUsername("admin").password("{noop}phoenix123").roles("ADMIN").build();
//			UserDetails regularUser = 
//					User.withUsername("regular").password("{noop}phoenix123").roles("USER").build();
//			InMemoryUserDetailsManager userDetailsManager = 
//					new InMemoryUserDetailsManager(adminUser, regularUser);
//			return userDetailsManager;
//                   
//        }
		
		@Bean
		public AuthenticationProvider authProvider() {
			
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
			provider.setUserDetailsService(userDetailsService);
			return provider;
		}
		
		
		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
			return config.getAuthenticationManager();
        }
		}


