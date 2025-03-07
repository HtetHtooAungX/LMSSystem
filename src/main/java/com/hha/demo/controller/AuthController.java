package com.hha.demo.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hha.demo.dto.input.LoginDto;
import com.hha.demo.dto.input.SignUpDto;
import com.hha.demo.entity.User;
import com.hha.demo.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService service;

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@PostMapping("/check-login")
	public String checkLogin(@ModelAttribute LoginDto dto) {
		
		StringBuilder decryptedPassword = new StringBuilder();
		for (String s : dto.getPassword().split("")) {
			char c = (char) (s.codePointAt(0)-2);
			decryptedPassword.append(c);
		}
//		System.out.println(decryptedPassword);
		
		User user = service.findByUserNameOrEmail(dto.getUsername());
		if (null != user && StringUtils.hasText(decryptedPassword)) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					decryptedPassword,
					user.getRole().stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return "redirect:/";
		}
		
		return "login";
	}
	
	@GetMapping("/after-logout")
	public String logout() {
		return "logout";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new SignUpDto());
		return "signup";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute SignUpDto user) {
		service.register(SignUpDto.to(user));
		return "redirect:/auth/login";
	}
}
