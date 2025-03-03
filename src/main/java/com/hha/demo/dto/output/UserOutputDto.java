package com.hha.demo.dto.output;

import com.hha.demo.entity.User;

import lombok.Data;

@Data
public class UserOutputDto {

	private int id;
	private String name;
	private String address;
	
	public static UserOutputDto from(User u) {
		UserOutputDto uo = new UserOutputDto();
		uo.setId(u.getId());
		uo.setName(u.getName());
		uo.setAddress(u.getAddress());
		return uo;
	}
}
