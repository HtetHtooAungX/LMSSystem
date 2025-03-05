package com.hha.demo.dto.input;

import com.hha.demo.entity.Book;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {

	private String name;
	private String author;
	
	public static Book from(BookDto dto) {
		return new Book(dto.getName(), dto.getAuthor());
	}

	public BookDto(String name, String author) {
		this.name = name;
		this.author = author;
	}
}
