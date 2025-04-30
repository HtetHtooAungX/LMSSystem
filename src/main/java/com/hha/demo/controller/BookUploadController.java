package com.hha.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hha.demo.dto.BookUploadDto;
import com.hha.demo.dto.output.BookSearchDto;
import com.hha.demo.dto.output.PageResult;
import com.hha.demo.service.BookUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookupload")
public class BookUploadController {

	private final BookUploadService service;
	
	@PostMapping("/save")
	public ResponseEntity<BookUploadDto> saveBookUpload(@RequestBody BookUploadDto uploadDto) {
		System.out.println(uploadDto);
		return new ResponseEntity<BookUploadDto>(service.saveUpload(uploadDto), HttpStatus.OK);
	}
	
	@GetMapping({"/find/{currentPage}/{size}", "/find/{currentPage}/{size}/{colName}", "/find/{currentPage}/{size}/{colName}/{desc}"})
	public PageResult<BookUploadDto> findByPagination(
											@PathVariable int currentPage,
											@PathVariable int size,
											@PathVariable Optional<String> colName,
											@PathVariable(required = false) boolean desc) {
		return service.findByPagination(currentPage, size, colName.orElse(""), desc);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<BookUploadDto> findById(@PathVariable int id) {
		return new ResponseEntity<BookUploadDto>(service.findById(id), HttpStatus.OK);
	}
}
