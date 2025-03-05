package com.hha.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hha.demo.exception.LMSException;
import com.hha.demo.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		return repo.findByUsernameOrEmail(username)
				.map(user -> User.builder()
						.username(user.getUsername())
						.password(user.getPassword())
						.authorities(user.getRole().name())
						.build()
					).orElseThrow(() -> new LMSException("Username or Email not found!"));
	}

}
