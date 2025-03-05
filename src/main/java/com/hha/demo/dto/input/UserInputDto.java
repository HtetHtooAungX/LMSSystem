package com.hha.demo.dto.input;

import com.hha.demo.entity.User;

import lombok.Data;

@Data
public class UserInputDto {

	private String name;
	private String username;
	private String email;
	private String password;
	
	public static User to(UserInputDto ui) {
		User user = new User();
		user.setName(ui.getName());
		user.setEmail(ui.getEmail());
		user.setUsername(ui.getUsername());
		user.setPassword(ui.getPassword());
		return user;
	}
}
