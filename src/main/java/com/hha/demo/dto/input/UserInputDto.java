package com.hha.demo.dto.input;

import com.hha.demo.entity.User;

import lombok.Data;

@Data
public class UserInputDto {

	private String name;
	private String address;
	
	public static User to(UserInputDto ui) {
		User user = new User();
		user.setName(ui.getName());
		user.setAddress(ui.getAddress());
		return user;
	}
}
