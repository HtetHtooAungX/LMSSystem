package com.hha.demo.dto.output;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.hha.demo.dto.BookUploadDto;
import com.hha.demo.entity.Book;
import com.hha.demo.entity.BookUpload;

import lombok.Data;

@Data
public class PageResult<T> {
	private List<T> contents;
	private long totalItems;
	private int totalPages;
	private int currentPage;
	private int size;
	
	public List<Integer> getLinks() {
		List<Integer> links = new ArrayList<Integer>();
		Integer lastPage = totalPages - 1;
		links.add(currentPage);
		
		while (links.size() < 3 && links.get(0) > 0) {
			links.add(0, links.get(0) - 1);
		}
		
		while (links.size() < 5 && links.get(links.size() -1) < lastPage) {
			links.add(links.get(links.size() - 1) + 1);
		}
		
		while (links.size() < 5 && links.get(0) > 0) {
			links.add(0, links.get(0) - 1);
		}
		
		return links;
	}
	
	public static <X, Y> PageResult<Y> fromPage(Page<X> books, Function<X, Y> mapper) {
		PageResult<Y> result = new PageResult<>();
		result.setContents(books.getContent().stream().map(mapper).collect(Collectors.toList()));
		result.setTotalItems(books.getTotalElements());
		result.setTotalPages(books.getTotalPages());
		result.setCurrentPage(books.getNumber());
		result.setSize(books.getSize());
		return result;
	}
}
