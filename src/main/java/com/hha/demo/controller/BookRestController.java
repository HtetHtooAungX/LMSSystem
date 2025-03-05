package com.hha.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hha.demo.dto.input.BookDto;
import com.hha.demo.dto.output.BookSearchDto;
import com.hha.demo.dto.output.UpdateBookDto;
import com.hha.demo.service.BookService;

@RestController
@RequestMapping("/api/book")
@Profile("dev")
public class BookRestController {

	@Autowired
	private BookService service;
	
	@GetMapping("/find")
	public ResponseEntity<List<BookSearchDto>> findByNameOrAuthor(@RequestBody BookDto dto) {
		System.out.println(dto);
		return new ResponseEntity<List<BookSearchDto>>(service.findByNameOrAuthor(dto), HttpStatus.FOUND);
	}
}
