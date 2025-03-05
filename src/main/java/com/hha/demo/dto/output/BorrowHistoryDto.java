package com.hha.demo.dto.output;

import java.time.LocalDate;

import com.hha.demo.entity.BorrowHistory;

import lombok.Data;

@Data
public class BorrowHistoryDto {

	private int id;

	private String book;
	
	private String user;
	
	private LocalDate borrowAt;
	private LocalDate returnAt;
	
	public static BorrowHistoryDto from(BorrowHistory bh) {
		BorrowHistoryDto dto = new BorrowHistoryDto();
		dto.setId(bh.getId());
		dto.setUser(bh.getUser().getName());
		dto.setBook(bh.getBook().getName());
		dto.setBorrowAt(bh.getBorrowAt());
		dto.setReturnAt(bh.getReturnAt());
		
		return dto;
	}
}
