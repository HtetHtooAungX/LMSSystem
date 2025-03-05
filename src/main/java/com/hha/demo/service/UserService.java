package com.hha.demo.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hha.demo.dto.input.UserInputDto;
import com.hha.demo.dto.output.UserOutputDto;
import com.hha.demo.entity.User;
import com.hha.demo.exception.LMSException;
import com.hha.demo.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo repo;
	
	public UserOutputDto addUser(UserInputDto ui) {
		return UserOutputDto.from(repo.save(UserInputDto.to(ui)));
	}
	
	public UserOutputDto updateUserInfo(int id, UserInputDto ui) {
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
		
		return UserOutputDto.from(repo.save(user)); 
	}
	
	public List<UserOutputDto> findByName(String name) {
		return repo.findByName(name.toLowerCase().concat("%"))
				.stream().map(UserOutputDto::from)
				.collect(Collectors.toList());
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
}
