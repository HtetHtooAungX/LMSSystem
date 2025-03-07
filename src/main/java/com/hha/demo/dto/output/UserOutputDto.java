package com.hha.demo.dto.output;

import java.util.List;

import com.hha.demo.entity.User;
import com.hha.demo.entity.User.Role;

import lombok.Data;

@Data
public class UserOutputDto {

	private int id;
	private String name;
	private String username;
	private String email;
	private List<Role> role;
	
	public static UserOutputDto from(User u) {
		UserOutputDto uo = new UserOutputDto();
		uo.setId(u.getId());
		uo.setName(u.getName());
		uo.setEmail(u.getEmail());
		uo.setUsername(u.getUsername());
		uo.setRole(u.getRole());
		return uo;
	}
}
