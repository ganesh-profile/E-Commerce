package com.E_CommerceApplication.App.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.E_CommerceApplication.App.configuration.UserInfoConfig;
import com.E_CommerceApplication.App.exception.ResourceNotFoundException;
import com.E_CommerceApplication.App.models.User;
import com.E_CommerceApplication.App.repositories.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByEmail(username);
		
		return user.map(UserInfoConfig::new).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
	}
}