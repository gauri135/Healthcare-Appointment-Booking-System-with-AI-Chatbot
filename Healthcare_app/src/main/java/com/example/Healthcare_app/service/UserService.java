package com.example.Healthcare_app.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserService {
	
	private  BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	@Bean
    UserDetailsService users() {
	    UserDetails user = User.withUsername("user")
	        .password(bCryptPasswordEncoder.encode("password"))
	        .roles("USER")
	        .build();
	    return new InMemoryUserDetailsManager(user);
	}



}
