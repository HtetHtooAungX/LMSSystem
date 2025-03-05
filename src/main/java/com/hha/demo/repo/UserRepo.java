package com.hha.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hha.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	@Query("select u from User u where lower(u.name) like :name")
	List<User> findByName(@Param("name") String name);

	Optional<User> findById(Integer userId);
	
	@Query("select u from User u where u.username = :keyword or u.email = :keyword")
	Optional<User> findByUsernameOrEmail(@Param("keyword") String keyword);
}
