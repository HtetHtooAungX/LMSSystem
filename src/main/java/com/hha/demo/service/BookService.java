package com.hha.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hha.demo.dto.dataTables.input.DataTablesServerSideInput;
import com.hha.demo.dto.input.BookDto;
import com.hha.demo.dto.output.BookSearchDto;
import com.hha.demo.dto.output.UpdateBookDto;
import com.hha.demo.entity.Book;
import com.hha.demo.entity.User.Role;
import com.hha.demo.exception.LMSException;
import com.hha.demo.repo.BookRepo;
import com.hha.demo.repo.specification.BookSpecification;

@Service
public class BookService {

	@Autowired
	private BookRepo repo;

	@Secured("ROLE_LIBRANIAN")
	public void deleteBook(int id) {
		repo.deleteById(id);
	}

	@Secured("ROLE_LIBRANIAN")
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

	@Secured("ROLE_LIBRANIAN")
	public Book save(Book book) {
		return repo.save(book);
	}

	public List<BookSearchDto> findByNameOrAuthor(BookDto dto) {
		Specification<Book> spec = namedOrEmail(dto);

		if (null != spec) {
			return repo.findAll(spec).stream().map(BookSearchDto::from).collect(Collectors.toList());
		}

		return repo.findAll().stream().map(BookSearchDto::from).collect(Collectors.toList());
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
				spec.or(spec = BookSpecification.hasAuthor(dto.getAuthor()));
			}
		}

		if (isUser()) {
			if (null == spec) {
				spec = BookSpecification.checkRole(Role.ROLE_USER);
			} else {
				spec.and(BookSpecification.checkRole(Role.ROLE_USER));
			}

		}
		return spec;
	}

	private boolean isUser() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_USER"));
	}

	public List<BookSearchDto> findByNameOrAuthor(DataTablesServerSideInput input) {

		String search = (String) input.getSearch().get("value");
		BookDto dto = new BookDto();
		dto.setAuthor(search);
		dto.setName(search);
		Specification<Book> spec = namedOrEmailSS(search, input.getOrder().get(0));
		
		return repo.findAll(spec).stream().map(BookSearchDto::from).collect(Collectors.toList());
	}

	private Specification<Book> namedOrEmailSS(String search, Map<String, String> order) {

		Specification<Book> spec = BookSpecification.dataTablesSpec(order);

		if (null != search && StringUtils.hasLength(search)) {
			spec = spec.and(BookSpecification.hasName(search));
			spec = spec.or(BookSpecification.hasAuthor(search));
		}

		if (isUser()) {
			spec = spec.and(BookSpecification.checkRole(Role.ROLE_USER));
		}
		return spec;
	}
}
