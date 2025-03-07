package com.hha.demo.dto.output;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.hha.demo.entity.BorrowHistory;

import lombok.Data;

@Data
public class BorrowHistoryDto {

	private int id;

	private String book;
	
	private String user;
	
	private String borrowAt;
	private String returnAt;
	
	public static BorrowHistoryDto from(BorrowHistory bh) {
		BorrowHistoryDto dto = new BorrowHistoryDto();
		dto.setId(bh.getId());
		dto.setUser(bh.getUser().getName());
		dto.setBook(bh.getBook().getName());
		dto.setBorrowAt(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
				  .format(bh.getBorrowAt()));
		dto.setReturnAt(null != bh.getReturnAt() ? DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
				  .format(bh.getReturnAt()) : null);
		
		return dto;
	}
}
