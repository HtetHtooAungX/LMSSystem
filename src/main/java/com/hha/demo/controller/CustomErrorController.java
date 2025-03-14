package com.hha.demo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("dev")
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String error(HttpServletRequest request) {
		System.out.println(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		return "accessDenied";
	}
}
