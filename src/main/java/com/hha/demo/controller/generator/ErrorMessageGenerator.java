package com.hha.demo.controller.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ErrorMessageGenerator {

	public static void generateUserErrorMessage(BindingResult result, RedirectAttributes reAttributes) {
		
		if (result.hasFieldErrors("name")) {
			List<String> nameErr = new ArrayList<>();
			result.getFieldErrors("name").forEach(err -> nameErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("nameErr", nameErr);
		}
		
		if (result.hasFieldErrors("username")) {
			List<String> usernameErr = new ArrayList<>();
			result.getFieldErrors("username").forEach(err -> usernameErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("usernameErr", usernameErr);
		}
		
		if (result.hasFieldErrors("email")) {
			List<String> emailErr = new ArrayList<>();
			result.getFieldErrors("email").forEach(err -> emailErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("emailErr", emailErr);
		}
		
		if (result.hasFieldErrors("password")) {
			List<String> passwordErr = new ArrayList<>();
			result.getFieldErrors("password").forEach(err -> passwordErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("passwordErr", passwordErr);
		}
		
		if (result.hasFieldErrors("role")) {
			List<String> roleErr = new ArrayList<>();
			result.getFieldErrors("role").forEach(err -> roleErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("roleErr", roleErr);
		}
		
		if (result.hasFieldErrors("oldPassword")) {
			List<String> oldPasswordErr = new ArrayList<>();
			result.getFieldErrors("oldPassword").forEach(err -> oldPasswordErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("oldPasswordErr", oldPasswordErr);
		}
		
		if (result.hasFieldErrors("newPassword")) {
			List<String> newPasswordErr = new ArrayList<>();
			result.getFieldErrors("newPassword").forEach(err -> newPasswordErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("newPasswordErr", newPasswordErr);
		}
		
		if (result.hasFieldErrors("reNewPassword")) {
			List<String> reNewPasswordErr = new ArrayList<>();
			result.getFieldErrors("reNewPassword").forEach(err -> reNewPasswordErr.add(err.getDefaultMessage()));
			reAttributes.addFlashAttribute("reNewPasswordErr", reNewPasswordErr);
		}
	}
}
