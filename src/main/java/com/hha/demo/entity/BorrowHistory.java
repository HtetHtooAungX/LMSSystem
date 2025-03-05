package com.hha.demo.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BorrowHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Book book;
	
	@ManyToOne
	private User user;
	
	private LocalDate borrowAt;
	private LocalDate returnAt;
	
	public BorrowHistory(LocalDate borrowAt, LocalDate returnAt) {
		this.borrowAt = borrowAt;
		this.returnAt = returnAt;
	}
}
