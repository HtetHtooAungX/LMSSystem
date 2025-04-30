package com.hha.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hha.demo.dto.dataTables.input.DataTablesServerSideInput;
import com.hha.demo.dto.dataTables.output.DataTablesServerSideOutput;
import com.hha.demo.dto.input.BookDto;
import com.hha.demo.dto.output.BookSearchDto;
import com.hha.demo.dto.output.PageResult;
import com.hha.demo.entity.Book;
import com.hha.demo.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookRestController {

	@Autowired
	private BookService service;
	
	@GetMapping("/find")
	public ResponseEntity<List<BookSearchDto>> findByNameOrAuthor(@RequestBody Optional<BookDto> dto) {
		return new ResponseEntity<List<BookSearchDto>>(service.findByNameOrAuthor(dto.orElseGet(() -> new BookDto())), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/find/ss")
	public DataTablesServerSideOutput<BookSearchDto> findByNameOrAuthorWithServerSide(
										DataTablesServerSideInput input) {
		
		List<BookSearchDto> list = service.findByNameOrAuthor(input);
		DataTablesServerSideOutput<BookSearchDto> output = new DataTablesServerSideOutput<BookSearchDto>(
				input.getDraw(), 
				list.size(), 
				list.size(), 
				list);
		return output.listSubList(input.getStart(), input.getLength());
	}
	
	@GetMapping({"/find/{currentPage}/{size}", "/find/{currentPage}/{size}/{colName}", "/find/{currentPage}/{size}/{colName}/{desc}"})
	public PageResult<BookSearchDto> findByPagination(
											@PathVariable int currentPage,
											@PathVariable int size,
											@PathVariable Optional<String> colName,
											@PathVariable(required = false) boolean desc) {
		return service.findByPagination(currentPage, size, colName.orElse(""), desc);
	}
}
