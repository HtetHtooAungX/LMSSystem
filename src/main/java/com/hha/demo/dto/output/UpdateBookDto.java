package com.hha.demo.dto.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.hha.demo.entity.Book;
import com.hha.demo.entity.BorrowHistory;

import lombok.Data;

@Data
public class UpdateBookDto {

	private int id;
	private String name;
	private String author;
	
	private List<BorrowHistoryDto> borrowHistory = new ArrayList<BorrowHistoryDto>();
	
	public void addAllBh(List<BorrowHistoryDto> borrowHistoryDtos) {
		this.borrowHistory.addAll(borrowHistoryDtos);
	}
	
	public static UpdateBookDto from(Book book, List<BorrowHistory> list) {
		UpdateBookDto dto = new UpdateBookDto();
		
		dto.setId(book.getId());
		dto.setName(book.getName());
		dto.setAuthor(book.getAuthor());
		dto.setBorrowHistory(list.stream().map(BorrowHistoryDto::from).collect(Collectors.toList()));
		
		return dto;
	}
}
