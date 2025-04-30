package com.hha.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hha.demo.dto.BookUploadDto;
import com.hha.demo.dto.output.BookSearchDto;
import com.hha.demo.dto.output.PageResult;
import com.hha.demo.repo.BookUploadRepo;
import com.hha.demo.entity.Book;
import com.hha.demo.entity.BookUpload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookUploadService {
	
	private final BookUploadRepo repo;

	public BookUploadDto saveUpload(BookUploadDto upload) {
		
		BookUpload entity = BookUploadDto.fromDto(upload);

		return BookUploadDto.toDto(repo.save(entity), false);
	}

	public PageResult<BookUploadDto> findByPagination(int currentPage, int size, String colName, boolean desc) {
		
		final Pageable pageable;
		
		if (StringUtils.hasText(colName)) {
			switch (colName) {
				case "name":
				case "author":
					pageable = getPageRequest(currentPage, size, colName, desc);
					break;
				default:
					pageable = getPageRequest(currentPage, size, "id", desc);
					break;
			}
		} else {
			pageable = PageRequest.of(currentPage, size);
		}
		
		Page<BookUpload> books = repo.findAll(pageable);
		
		PageResult<BookUploadDto> result = PageResult.fromPage(books, (upload) -> BookUploadDto.toDto(upload, false));
		return result;
	}

	private Pageable getPageRequest(int currentPage, int size, String colName, boolean desc) {
		return desc ? PageRequest.of(currentPage, size, Sort.by(colName).descending()) : PageRequest.of(currentPage, size, Sort.by(colName).ascending());
	}

	public BookUploadDto findById(int id) {
		return repo.findById(id).map((book) -> BookUploadDto.toDto(book, true)).orElse(null);
	}
	
}
