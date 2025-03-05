package com.hha.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hha.demo.dto.input.SignUpDto;
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
