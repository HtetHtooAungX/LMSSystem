package com.hha.demo.dto.input;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookBorrowDto {

	private Integer bookId;
	private String username;
	private LocalDate borrowAt;
	private LocalDate returnAt;
}
