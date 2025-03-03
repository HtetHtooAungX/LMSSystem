package com.hha.demo.entity;

import java.time.LocalDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BorrowHistory {
	
	@EmbeddedId
	private BorrowHistoryPk bhPk;

	@ManyToOne
	@MapsId("bookId")
	@JoinColumn(name = "book_id", insertable = false, updatable = false)
	private Book book;
	
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;
	
	private LocalDate returnAt;
}
