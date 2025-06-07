package com.E_CommerceApplication.App.service;

import java.util.Optional;

import com.E_CommerceApplication.App.DTOs.UserDTO;
import com.E_CommerceApplication.App.exception.ResourceNotFoundException;
import com.E_CommerceApplication.App.models.User;
import com.E_CommerceApplication.App.repositories.UserRepo;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByEmail(username);

		return (UserDetails) user.map((element) -> modelMapper.map(element, UserDTO.class)).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
	}
}