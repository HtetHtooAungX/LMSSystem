package com.hha.demo.repo;

import com.hha.demo.entity.Book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Integer>{

	Optional<Book> findById(String bookId);

}
