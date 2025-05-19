package com.gl.mdr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gl.mdr.repo.app.UserRepository;

@Service
public class UserDetailsServiceImpl {
	@Autowired
	UserRepository userRepository;
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return username -> userRepository.findByUsername(username)
	    		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
