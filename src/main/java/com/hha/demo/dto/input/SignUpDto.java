package com.hha.demo.dto.input;

import com.hha.demo.entity.User;

import lombok.Data;

@Data
public class SignUpDto {

	private String name;
	private String username;
	private String email;
	private String password;
	
	
	public static User to(SignUpDto user) {
		User u = new User();
		u.setName(user.getName());
		u.setUsername(user.getUsername());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		return u;
	}
}
