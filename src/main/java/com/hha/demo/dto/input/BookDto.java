package com.hha.demo.dto.input;

import com.hha.demo.entity.Book;
import com.hha.demo.entity.User.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {

	private String name;
	private String author;
	private String visiable;
	
	public static Book from(BookDto dto) {
		return new Book(dto.getName(), dto.getAuthor(), Role.valueOf(dto.getVisiable()));
	}

	public BookDto(String name, String author, String visiable) {
		this.name = name;
		this.author = author;
		this.visiable = visiable;
	}
}
