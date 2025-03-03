package com.hha.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hha.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	@Query("select * from User u where lower(u.name) like :name")
	List<User> findByName(String name);

	Optional<User> findById(String userId);
}
