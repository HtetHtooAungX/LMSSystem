package com.hha.demo.dto.output;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserToken {
	private String username;
	private String role;
	private String token;
	
	public boolean getIsAlreadyLogin() {
		return !StringUtils.hasLength(username);
	}
}
