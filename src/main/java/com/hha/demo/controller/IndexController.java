package com.hha.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "redirect:/book";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
}
