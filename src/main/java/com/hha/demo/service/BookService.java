package com.hha.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hha.demo.dto.input.BookDto;
import com.hha.demo.dto.output.BookSearchDto;
import com.hha.demo.dto.output.UpdateBookDto;
import com.hha.demo.entity.Book;
import com.hha.demo.exception.LMSException;
import com.hha.demo.repo.BookRepo;
import com.hha.demo.repo.specification.BookSpecification;

@Service
public class BookService {
	
	@Autowired
	private BookRepo repo;
	
	@Secured("LIBRANIAN")
	public void deleteBook(int id) {
		repo.deleteById(id);
	}
	
	@Secured("LIBRANIAN")
	public void update(int id, UpdateBookDto ubook) {
		Book book = repo.findById(id).orElseThrow(() -> new LMSException("invalid Id"));
		book.setId(id);
		book.setName(ubook.getName());
		book.setAuthor(ubook.getAuthor());
		repo.save(book);
	}
	
	public Optional<Book> findById(int bookId) {
		return repo.findById(bookId);
	}
	
	@Secured("LIBRANIAN")
	public Book save(BookDto dto) {
		return repo.save(BookDto.from(dto));
	}
	
	public List<BookSearchDto> findByNameOrAuthor(BookDto dto) {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		Specification<Book> spec = namedOrEmail(dto);
		
		if (null != spec) {
			return repo.findAll(spec).stream()
					.map(BookSearchDto::from)
					.collect(Collectors.toList());
		}
		
		return repo.findAll().stream()
				.map(BookSearchDto::from)
				.collect(Collectors.toList());
	}
	
	private Specification<Book> namedOrEmail(BookDto dto) {
		Specification<Book> spec = null;
		
		if (null != dto.getName() && StringUtils.hasLength(dto.getName())) {
			spec = BookSpecification.hasName(dto.getName());
		}
		
		if (null != dto.getAuthor() && StringUtils.hasLength(dto.getAuthor())) {
			if (null == spec) {
				spec = BookSpecification.hasAuthor(dto.getAuthor());
			} else {
				spec.and(spec = BookSpecification.hasAuthor(dto.getAuthor()));
			}
		}
		return spec;
	}
}
