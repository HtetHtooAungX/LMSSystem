package com.hha.demo.dto.input;


import lombok.Data;

@Data
public class PasswordChgDto {
	
	private String oldPassword;
	private String newPassword;
	private String reNewPassword;
	
}
