package com.hha.demo.repo.specification;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.hha.demo.entity.Book;
import com.hha.demo.entity.User.Role;

public class BookSpecification {

	
	public static Specification<Book> hasName(String name) {
		return (root, query, builder) -> builder.like(builder.lower(root.get("name")) , "%" + name.toLowerCase().concat("%"));
	}
	
	public static Specification<Book> hasAuthor(String author) {
		return (root, query, builder) ->  builder.like(builder.lower(root.get("author")) , "%" + author.toLowerCase().concat("%"));
	}

	public static Specification<Book> checkRole(Role visiable) {
		return (root, query, builder) -> builder.equal(root.get("visiable"), visiable);
	}

	public static Specification<Book> dataTablesSpec(Map<String, String> order) {
		return (root, query, builder) -> {
			query.orderBy(checkDir(order, builder, root));
			return builder.conjunction();
		};
	}
	
	private static Order checkDir(Map<String, String> order, CriteriaBuilder cb, Root<Book> root) {
		String dir = order.get("dir");
		
		Integer colId = Integer.valueOf(order.get("column"));
		String colName = colId == 0 ? "id" : colId == 1 ? "name" : "author";
		
		return "asc".equals(dir) ? cb.asc(root.get(colName)) : cb.desc(root.get(colName));
	}
}
