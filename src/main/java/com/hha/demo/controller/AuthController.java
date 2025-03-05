package com.hha.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/book";
	}
}
