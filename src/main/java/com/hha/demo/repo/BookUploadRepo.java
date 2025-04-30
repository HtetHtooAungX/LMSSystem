package com.hha.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hha.demo.entity.BookUpload;

public interface BookUploadRepo extends JpaRepository<BookUpload, Integer>, PagingAndSortingRepository<BookUpload, Integer>{

}
