package com.hha.demo.entity.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.hha.demo.dto.input.PasswordChgDto;
import com.hha.demo.entity.User;
import com.hha.demo.entity.User.Role;
import com.hha.demo.repo.UserRepo;

@Component
public class UserValidator {
	@Autowired
	private UserRepo repo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void validate(User user, BindingResult result, Authentication authentication) {
		validateAuthority(user, result, authentication);
		validateEmail(user, result);
		validateUsername(user, result);
	}
	
	public void validateUpdate(User user, BindingResult result, Authentication authentication) {
		User u = repo.findById(user.getId()).get();
		if (!u.getEmail().equals(user.getEmail())) {
			System.out.println("pass1");
			validateEmail(user, result);
		}
		
		if (!u.getUsername().equals(user.getUsername())) {
			System.out.println("pass2");
			validateUsername(user, result);
		}
		validateAuthority(user, result, authentication);
	}
	

	public void validateChangePassword(int id, PasswordChgDto pwdDto, BindingResult result) {
		
		emptyStringCheck(pwdDto, result);
		
		if (StringUtils.hasText(pwdDto.getNewPassword()) &&
				StringUtils.hasText(pwdDto.getReNewPassword())) {
			
			if (!pwdDto.getNewPassword().equals(pwdDto.getReNewPassword())) {
				
				System.out.println("password and confirm password aren't same");
				result.rejectValue("reNewPassword", "not equal",
						"incorrect reEntered password!");
			} else {
				if (StringUtils.hasText(pwdDto.getOldPassword())) {
					
					if(pwdDto.getOldPassword().equals(pwdDto.getNewPassword())) {
						
						System.out.println("Ohh, you got right");
						result.rejectValue("newPassword", "invalid",
								"old password and new password cannot be the same!");
					} else {
						
						User user = repo.findById(id).get();
						if (!checkPassword(pwdDto.getOldPassword(), user.getPassword())) {
							
							System.out.println("original password and incoming old password aren't same");
							result.rejectValue("oldPassword", "incorrect", 
									"incorrect old password!");
						}
					}
				}
			}
		}
	}

	private void emptyStringCheck(PasswordChgDto pwdDto, BindingResult result) {
		if (!StringUtils.hasText(pwdDto.getOldPassword())) {
			result.rejectValue("oldPassword", "empty string", "oldPassword can't be null!");
		}
		
		if (!StringUtils.hasText(pwdDto.getNewPassword())) {
			result.rejectValue("newPassword", "empty string", "newPassword can't be null!");
		}
		
		if (!StringUtils.hasText(pwdDto.getReNewPassword())) {
			result.rejectValue("reNewPassword", "empty string", "reNewPassword can't be null!");
		}
	}

	private boolean checkPassword(String dtoPass, String ogPass) {
		return passwordEncoder.matches(dtoPass, ogPass);
	}

	private void validateAuthority(User user, BindingResult result, Authentication authentication) {
		if (!"anonymousUser".equals(authentication.getPrincipal())) {
			if (validateAddUserRole(user, authentication)) {
				System.out.println("role");
				result.rejectValue("role", "unauthorize", "Not Enough Authoritiy!");
			}
		}
	}

	private void validateUsername(User user, BindingResult result) {
		if (StringUtils.hasLength(user.getUsername())) {
			User sameUserName = repo.findByUsernameOrEmail(user.getUsername()).orElse(null);
			
			if (null != sameUserName) {
				result.rejectValue("username", "duplicate", "Username already existed!");
			}
		}
	}

	private void validateEmail(User user, BindingResult result) {
		if (StringUtils.hasLength(user.getEmail())) {
			User sameEmail = repo.findByUsernameOrEmail(user.getEmail()).orElse(null);
			
			if (null != sameEmail) {
				result.rejectValue("email", "duplicate", "Email already existed!");
			}
		}
	}

	private boolean validateAddUserRole(User user, Authentication authentication) {
		long count = authentication.getAuthorities()
						.stream()
						.filter(grant -> user.getRole().ordinal() > Role.valueOf(grant.getAuthority()).ordinal())
						.count();
		
		return count == 0 ? true : false;
	}
}
