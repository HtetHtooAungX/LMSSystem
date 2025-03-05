package com.hha.demo.repo.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hha.demo.entity.Book;

public class BookSpecification {
	
	public static Specification<Book> hasName(String name) {
		return (root, query, builder) -> builder.like(builder.lower(root.get("name")) , "%" + name.toLowerCase() + "%");
	}
	
	public static Specification<Book> hasAuthor(String author) {
		return (root, query, builder) ->  builder.like(builder.lower(root.get("author")) , author.toLowerCase().concat("%"));
	}
}
