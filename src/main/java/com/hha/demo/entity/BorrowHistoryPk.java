package com.hha.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class BorrowHistoryPk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "book_id")
	private int bookId;
	@Column(name = "user_id")
	private int userId;
	
	private LocalDate borrowAt;
}
