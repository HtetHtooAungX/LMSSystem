package com.hha.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.hha.demo.dto.dataTables.input.DataTablesServerSideInput;
import com.hha.demo.dto.input.PasswordChgDto;
import com.hha.demo.dto.input.UserInputDto;
import com.hha.demo.dto.output.UserOutputDto;
import com.hha.demo.entity.User;
import com.hha.demo.entity.User.Role;
import com.hha.demo.exception.LMSException;
import com.hha.demo.repo.UserRepo;
import com.hha.demo.repo.specification.UserSpecification;

@Service
public class UserService {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public User findByUserNameOrEmail(String keyword) {
		return repo.findByUsernameOrEmail(keyword).orElseGet(null);
	}
	
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(null == user.getRole() ? Role.ROLE_USER : user.getRole());
		return repo.save(user);
	}
	
	public UserOutputDto addUser(UserInputDto ui) {
		return UserOutputDto.from(repo.save(UserInputDto.to(ui)));
	}
	
	@Secured({"ADMIN","SUPER_ADMIN"})
	public UserOutputDto updateUserInfo(int id, User ui) {
		User user = repo.findById(id).orElseThrow(() -> new LMSException("Invalid user id."));
		
		user.setId(id);
		
		if (null != ui.getName()) {
			user.setName(ui.getName());
		}
		
		if (null != ui.getEmail()) {
			user.setEmail(ui.getEmail());
		}
		
		if (null != ui.getUsername()) {
			user.setUsername(ui.getUsername());
		}
		
		if (null != ui.getPassword()) {
			user.setPassword(ui.getPassword());
		}
		user.setRole(ui.getRole());
		System.out.println("done");
		return UserOutputDto.from(repo.save(user)); 
	}
	
	@Secured({"ADMIN","SUPER_ADMIN"})
	public void changePassword(int id, PasswordChgDto pwdDto) {
		User user = repo.findById(id).get();
		user.setPassword(encoder.encode(pwdDto.getNewPassword()));
		repo.save(user);
	}
	
	public List<UserOutputDto> findByName(String name) {
		return repo.findByName(name.toLowerCase().concat("%"))
				.stream().map(UserOutputDto::from)
				.collect(Collectors.toList());
	}
	
	public Optional<User> findById(int id) {
		return repo.findById(id);
	}
	
	public String deleteUser(int id) {
		try {
			User user = repo.findById(id).orElseThrow(() -> new LMSException("Invalid User Id."));
			repo.delete(user);
			return "Successfully deleted";
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
	public List<UserOutputDto> findUsersByUserNameAndEmail(DataTablesServerSideInput input) {
		String search = input.getSearch().get("value");
		Map<String, String> order = input.getOrder().get(0);
		Specification<User> spec = findUsersBySpecification(search, order);
		return repo.findAll(spec).stream()
				.map(UserOutputDto::from).collect(Collectors.toList());
	}

	private Specification<User> findUsersBySpecification(String search, Map<String, String> order) {
		Specification<User> spec = UserSpecification.dataTables(order);
		
		if (StringUtils.hasText(search)) {
			
			StringBuilder sb = new StringBuilder().append("%");
			sb.append(search.toLowerCase()).append("%");
			
			spec = spec.and(UserSpecification.searchEmail(sb.toString()));
			spec = spec.or(UserSpecification.searchUsername(sb.toString()));
		}
		
		if (isAdmin()) {
			spec = spec.and(UserSpecification.filterAdmin(Role.ROLE_ADMIN, Role.ROLE_SUPER_ADMIN));
		}
		return spec;
	}

	private boolean isAdmin() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
	}
}
