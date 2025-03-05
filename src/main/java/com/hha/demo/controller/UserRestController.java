package com.hha.demo.controller;

import java.util.List;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hha.demo.dto.input.UserInputDto;
import com.hha.demo.dto.output.UserOutputDto;
import com.hha.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
@Profile("dev")
public class UserRestController {

	@Autowired
	private UserService uService;
	
	@PostMapping("/add")
	private ResponseEntity<UserOutputDto> addUser(@RequestBody UserInputDto ui) {
		return new ResponseEntity<UserOutputDto>(uService.addUser(ui), HttpStatus.CREATED);
	}
	
	@GetMapping("/find/{name}")
	private ResponseEntity<List<UserOutputDto>> findByName(@PathVariable String name) {
		return new ResponseEntity<List<UserOutputDto>>(uService.findByName(name), HttpStatus.FOUND);
	}
	
	@PutMapping("/update")
	private ResponseEntity<UserOutputDto> updateUserInfo(@RequestParam int id,@RequestBody UserInputDto ui) {
		return new ResponseEntity<UserOutputDto>(uService.updateUserInfo(id, ui), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	private ResponseEntity<String> deleteUser(@PathVariable int id) {
		return new ResponseEntity<String>(uService.deleteUser(id), HttpStatus.OK);
	}
}
