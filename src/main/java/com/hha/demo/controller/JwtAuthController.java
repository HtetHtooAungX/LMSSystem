package com.hha.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hha.demo.dto.input.LoginDto;
import com.hha.demo.dto.output.UserToken;
import com.hha.demo.jwt.service.JwtAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class JwtAuthController {
	private final JwtAuthService service;

	@PostMapping("/login")
	public ResponseEntity<UserToken> login(@RequestBody LoginDto loginDto) {
		UserToken userToken = service.login(loginDto);
		return new ResponseEntity<UserToken>(userToken, userToken.getIsAlreadyLogin() ? HttpStatus.CONFLICT : HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<UserToken> logout(@RequestBody UserToken userToken) {
		return new ResponseEntity<UserToken>(new UserToken(null, null, null), service.logout(userToken));
	}
}
