package com.hha.demo.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hha.demo.entity.BorrowHistory;

public interface BorrowHistoryRepo extends JpaRepository<BorrowHistory, Integer>{

	@Query("select bh from BorrowHistory bh where bh.id = :id and bh.returnAt = null")
	Optional<BorrowHistory> findBorrowHistoryById(@Param("id") int id);
	
	@Query("select bh from BorrowHistory bh where bh.book.id= :bookId order by bh.returnAt desc")
	List<BorrowHistory> findHistoryByBookId(@Param("bookId") int bookId);
}
