package com.hha.demo.repo;

import com.hha.demo.entity.Book;

import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface BookRepo extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book>, PagingAndSortingRepository<Book, Integer> {

	Optional<Book> findById(Integer bookId);
}
