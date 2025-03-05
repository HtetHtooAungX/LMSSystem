package com.hha.demo.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hha.demo.entity.BorrowHistory;

public interface BorrowHistoryRepo extends JpaRepository<BorrowHistory, Integer>{

	@Query("select bh from BorrowHistory bh where bh.returnAt = null")
	Optional<BorrowHistory> findBorrowHistoryById(int id);
}
