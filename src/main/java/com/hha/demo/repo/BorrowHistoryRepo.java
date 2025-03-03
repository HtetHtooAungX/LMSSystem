package com.hha.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hha.demo.entity.BorrowHistory;
import com.hha.demo.entity.BorrowHistoryPk;

public interface BorrowHistoryRepo extends JpaRepository<BorrowHistory, BorrowHistoryPk>{

}
