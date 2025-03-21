package com.hha.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hha.demo.dto.input.BookBorrowDto;
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
	
	@GetMapping("/find")
	public String find() {
		return "dataTable";
	}
	
	@GetMapping
	public String viewAll(Model model) {
		model.addAttribute("user");
		if (!model.containsAttribute("books")) {
			model.addAttribute("books", service.findByNameOrAuthor(new BookDto()));
		}
		model.addAttribute("findBookDto", new BookDto());
		return "bookResult";
	}
	
	@PostMapping("/search")
	public String findBooks(@ModelAttribute("findBookDto") BookDto BookDto, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("books", service.findByNameOrAuthor(BookDto));
		return "redirect:/book";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		service.deleteBook(id);
		return "redirect:/book";
	}
	
	@GetMapping("/view/{id}")
	public String viewBook(Model model, @PathVariable int id) {
		UpdateBookDto book = UpdateBookDto.from(service.findById(id).get(), bhService.findBorrowHistoryByBookId(id));
		model.addAttribute("book", book);
		if (book.getBorrowHistory().isEmpty()
				|| book.getBorrowHistory().get(book.getBorrowHistory().size() - 1).getReturnAt() != null) {
			BookBorrowDto borrowDto = new BookBorrowDto();
			borrowDto.setBookId(id);
			model.addAttribute("borrowDto", borrowDto);
		}
		return "bookView";
	}
	
	@PostMapping("/update")
	public String updateBook(@ModelAttribute UpdateBookDto book) {
		service.update(book.getId(), book);
		return "redirect:/book";
	}
	
	@GetMapping("/create")
	public String createBook(Model model) {
		model.addAttribute("book", new Book());
		return "createBook";
	}
	
	@PostMapping("/create/save")
	public String saveBook(@ModelAttribute @Valid Book book, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			generateCreateBookErrorMessage(result, redirectAttributes);
			return "redirect:/book/create";
		}
		service.save(book);
		return "redirect:/book";
	}
	
	@GetMapping("/return/{id}/{borrowId}")
	public String returnBook(@PathVariable int id, @PathVariable int borrowId) {
		bhService.returnBook(borrowId);
		return "redirect:/book/view/"+id;
	}
	
	@PostMapping("/borrow")
	public String borrowBook(@ModelAttribute BookBorrowDto borrowDto) {
		bhService.borrowBook(borrowDto);
		return "redirect:/book/view/" + borrowDto.getBookId();
	}
	
	private void generateCreateBookErrorMessage(BindingResult result, RedirectAttributes redirectAttributes) {
		List<String> nameErr = new ArrayList<>();
		List<String> authorErr = new ArrayList<>();
		
		if (result.hasFieldErrors("name")) {
			result.getFieldErrors("name").stream().forEach(fe -> nameErr.add(fe.getDefaultMessage()));
			redirectAttributes.addFlashAttribute("nameErr", nameErr);
		}
		
		if (result.hasFieldErrors("author")) {
			result.getFieldErrors("author").stream().forEach(fe -> authorErr.add(fe.getDefaultMessage()));
			redirectAttributes.addFlashAttribute("authorErr", authorErr);
		}
	}
}
