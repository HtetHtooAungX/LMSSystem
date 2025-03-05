package com.hha.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hha.demo.dto.input.BookDto;
import com.hha.demo.dto.output.UpdateBookDto;
import com.hha.demo.entity.Book;
import com.hha.demo.service.BookService;
import com.hha.demo.service.BorrowHistoryService;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService service;
	
	@Autowired
	private BorrowHistoryService bhService;
	
	@GetMapping
	public String viewAll(Model model) {
		model.addAttribute("books", service.findByNameOrAuthor(new BookDto()));
		model.addAttribute("findBookDto", new BookDto());
		return "bookResult";
	}
	
	@PostMapping("/search")
	public String findBooks(Model model, @ModelAttribute("findBookDto") BookDto BookDto) {
		model.addAttribute("books", service.findByNameOrAuthor(BookDto));
		return "bookResult";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		service.deleteBook(id);
		return "redirect:/book";
	}
	
	@GetMapping("/view/{id}")
	public String viewBook(Model model, @PathVariable int id) {
		Book book = service.findById(id).get();
		model.addAttribute("book", UpdateBookDto.from(book));
		return "bookView";
	}
	
	@PostMapping("/update")
	public String updateBook(@ModelAttribute UpdateBookDto book) {
		service.update(book.getId(), book);
		return "redirect:/book";
	}
	
	@GetMapping("/create")
	public String createBook(Model model) {
		model.addAttribute("book", new BookDto());
		return "createBook";
	}
	
	@PostMapping("/create/save")
	public String saveBook(@ModelAttribute BookDto book) {
		service.save(book);
		return "redirect:/book";
	}
	
	@GetMapping("/return/{id}")
	public String returnBook(@PathVariable int id) {
		bhService.returnBook(id);
		return "redirect:/book/view/"+id;
	}
}
