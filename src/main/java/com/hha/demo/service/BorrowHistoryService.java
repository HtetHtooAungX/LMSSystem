package com.hha.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hha.demo.dto.input.BookBorrowDto;
import com.hha.demo.entity.Book;
import com.hha.demo.entity.BorrowHistoryPk;
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

	public String borrowBook(BookBorrowDto dto) {
		User user = uRepo.findById(dto.getUserId()).orElseThrow(() -> new LMSException("Invalid User Id."));
		Book book = bRepo.findById(dto.getBookId()).orElseThrow(() -> new LMSException("Invalid Book Id."));
		
		BorrowHistoryPk pk = new BorrowHistoryPk(user.getId(), book.getId(), LocalDate.now());
		return null;
	}
}
