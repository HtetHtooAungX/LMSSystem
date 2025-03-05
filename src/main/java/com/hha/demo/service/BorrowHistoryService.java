package com.hha.demo.service;


import java.time.LocalDate;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.hha.demo.dto.input.BookBorrowDto;
import com.hha.demo.entity.Book;
import com.hha.demo.entity.BorrowHistory;
import com.hha.demo.entity.User;
import com.hha.demo.exception.LMSException;
import com.hha.demo.repo.BookRepo;
import com.hha.demo.repo.BorrowHistoryRepo;
import com.hha.demo.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowHistoryService {
	
	private final BorrowHistoryRepo bhRepo;
	private final UserRepo uRepo;
	private final BookRepo bRepo;

	@Secured("LIBRANIAN")
	public BorrowHistory borrowBook(BookBorrowDto dto) {
		
		User user = uRepo.findByUsernameOrEmail(dto.getUsername()).orElseThrow(() -> new LMSException("Invalid UserName or Email."));
		Book book = bRepo.findById(dto.getBookId()).orElseThrow(() -> new LMSException("Invalid Book Id."));
		
		BorrowHistory bh = new BorrowHistory();
		bh.setBorrowAt(LocalDate.now());
		user.addBorrow(bh);
		book.addBorrow(bh);
		return bh;
	}
	
	@Secured("LIBRANIAN")
	public void returnBook(int id) {
		BorrowHistory bh = bhRepo.findBorrowHistoryById(id).orElseThrow(() -> new LMSException("Invalid Borrow History."));
		bh.setReturnAt(LocalDate.now());
		bhRepo.save(bh);
	}
}
