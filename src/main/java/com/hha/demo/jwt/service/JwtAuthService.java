package com.hha.demo.jwt.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hha.demo.controller.JwtAuthController;
import com.hha.demo.dto.input.LoginDto;
import com.hha.demo.dto.output.UserToken;
import com.hha.demo.entity.User;
import com.hha.demo.jwt.JwtProvider;
import com.hha.demo.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtAuthService {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private ConcurrentMap<String, String> loginUsers = new ConcurrentHashMap<String, String>();
			
	public UserToken login(LoginDto loginDto) {
		
		String username = loginDto.getUsername();
		
		loginUsers.forEach((key, value) -> log.info(key.concat(" login with ").concat(value)));
		
		if (loginUsers.containsKey(username)) {
			return new UserToken(null, null, null);
		}
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, loginDto.getPassword());
		
		Authentication authenticated = authenticationManager.authenticate(authentication);
		
		User user = userService.findByUserNameOrEmail(username);
		
		String token = jwtProvider.generate(authenticated);
		
		loginUsers.computeIfAbsent(username, (key) -> {
			return token;
		});
		
		return new UserToken(authenticated.getName(), user.getRole().name(), token);
	}

	public HttpStatus logout(UserToken userToken) {
		return userToken.getIsAlreadyLogin() ?  HttpStatus.BAD_REQUEST : 
			loginUsers.remove(userToken.getUsername(), userToken.getToken()) ?
				HttpStatus.OK : HttpStatus.BAD_REQUEST;
	}
}
