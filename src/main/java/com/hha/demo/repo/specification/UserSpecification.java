package com.hha.demo.repo.specification;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.hha.demo.entity.User;
import com.hha.demo.entity.User.Role;

public class UserSpecification {

	public static Specification<User> dataTables(Map<String, String> order) {
		return (root, query, builder) -> {
			query.orderBy(checkDir(order, builder, root));
			return builder.conjunction();
		};
	}

	private static Order checkDir(Map<String, String> order, CriteriaBuilder builder, Root<User> root) {
		String dir = order.get("dir");
		
		Integer colId = Integer.valueOf(order.get("column"));
		String colName = colId == 0 ? "id" : colId == 1 ? "username" : "email";
		
		return "asc".equals(dir) ? builder.asc(root.get(colName)) : builder.desc(root.get(colName));
	}

	public static Specification<User> searchUsername(String search) {
		return (root, query, builder) -> builder.like(builder.lower(root.get("username")), search);
	}
	
	public static Specification<User> searchEmail(String search) {
		return (root, query, builder) -> builder.like(builder.lower(root.get("email")), search);
	}

	public static Specification<User> filterAdmin(Role admin, Role superAdmin) {
		return (root, query, builder) -> {
			return builder.and(builder.notEqual(root.get("role"), admin), builder.notEqual(root.get("role"), superAdmin));
		};
	}
}
