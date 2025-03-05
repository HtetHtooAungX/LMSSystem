package com.hha.demo.dto.output;

import com.hha.demo.entity.Book;

import lombok.Data;

@Data
public class BookSearchDto {

	private int id;
	private String name;
	private String author;
	
	public static BookSearchDto from(Book book) {
		BookSearchDto dto = new BookSearchDto();
		
		dto.setId(book.getId());
		dto.setName(book.getName());
		dto.setAuthor(book.getAuthor());		
		return dto;
	}
}
