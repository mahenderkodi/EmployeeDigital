package com.example.EmployeeDegital.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.EmployeeDegital.entity.Users;
import com.example.EmployeeDegital.repository.UserRepositorey;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
private final UserRepositorey userRepositorey;
	
	public UserDetailsServiceImpl(UserRepositorey userRepositorey) {
		this.userRepositorey=userRepositorey;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> optionalUser=userRepositorey.findByUsername(username);
		if(optionalUser.isEmpty()) {
			 throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Users user=optionalUser.get();
		return new OurUser(user);
	}

}

