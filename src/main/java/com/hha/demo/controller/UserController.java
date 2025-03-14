package com.hha.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hha.demo.controller.generator.ErrorMessageGenerator;
import com.hha.demo.dto.dataTables.input.DataTablesServerSideInput;
import com.hha.demo.dto.dataTables.output.DataTablesServerSideOutput;
import com.hha.demo.dto.input.PasswordChgDto;
import com.hha.demo.dto.output.UserOutputDto;
import com.hha.demo.entity.User;
import com.hha.demo.entity.validation.UserValidator;
import com.hha.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	private final UserValidator userValidator;
	
	@GetMapping("manage")
	public String manageUser() {
		return "manageUsers";
	}
	
	@GetMapping("api")
	@ResponseBody
	public DataTablesServerSideOutput<UserOutputDto> manageUserApi(DataTablesServerSideInput input) {
		List<UserOutputDto> list = service.findUsersByUserNameAndEmail(input);
		DataTablesServerSideOutput<UserOutputDto> output = new DataTablesServerSideOutput<UserOutputDto>(
				input.getDraw(), 
				list.size(), 
				list.size(), 
				list);
		return output.listSubList(input.getStart(), input.getLength());
	}
	
	@GetMapping("/view/{id}")
	public String viewUser(Model model, @PathVariable int id) {
		model.addAttribute("user", service.findById(id).get());
		return "userDetail";
	}
	
	@PostMapping("/view/update")
	public String updateUser(@ModelAttribute @Valid User user,
			BindingResult result, RedirectAttributes reAttributes) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userValidator.validateUpdate(user, result, authentication);
		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			ErrorMessageGenerator.generateUserErrorMessage(result, reAttributes);
			return "redirect:/users/view/"+user.getId();
		}
		
		service.updateUserInfo(user.getId(), user);
		return "redirect:/users/manage";
	}
	
	@GetMapping("/{id}/pwdchg")
	public String updatePassword(Model model, @PathVariable int id) {
		model.addAttribute("pwdDto", new PasswordChgDto());
		return "pwdChange";
	}
	
	@PostMapping("/{id}/change")
	public String changePassword(@ModelAttribute PasswordChgDto pwdDto,
			@PathVariable int id, BindingResult result, RedirectAttributes reAttributes) {
		userValidator.validateChangePassword(id, pwdDto, result);
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			ErrorMessageGenerator.generateUserErrorMessage(result, reAttributes);
			return "redirect:/users/" + id + "/pwdchg";
		}
		
		service.changePassword(id, pwdDto);
		return "redirect:/users/view/" + id ;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id) {
		service.deleteUser(id);
		return "redirect:/users/manage";
	}
}
