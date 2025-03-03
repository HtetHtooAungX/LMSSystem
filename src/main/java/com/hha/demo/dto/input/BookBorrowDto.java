package com.hha.demo.dto.input;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookBorrowDto {

	private String bookId;
	private String userId;
	private LocalDate borrowAt;
}
