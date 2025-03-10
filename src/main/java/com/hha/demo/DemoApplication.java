package com.hha.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hha.demo.entity.Book;
import com.hha.demo.entity.User;
import com.hha.demo.entity.User.Role;
import com.hha.demo.entity.BorrowHistory;
import com.hha.demo.repo.BookRepo;
import com.hha.demo.repo.BorrowHistoryRepo;
import com.hha.demo.repo.UserRepo;
import com.hha.demo.security.event.ApplicationEventListener;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@Configuration
@RequiredArgsConstructor
public class DemoApplication {
	
	private final BookRepo bRepo;
	private final UserRepo uRepo;
	private final BorrowHistoryRepo bhRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Bean
	ApplicationRunner runner() {
		return args -> {
			List<Book> books = new ArrayList();
			books.add(new Book("Effective Java", "Joshua Bloch", Role.ROLE_USER));
			books.add(new Book("Clean Code", "Robert C. Martin", Role.ROLE_PREMIUM));
			books.add(new Book("Design Patterns", "Erich Gamma et al.", Role.ROLE_USER));
			books.add(new Book("The Pragmatic Programmer", "Andrew Hunt", Role.ROLE_USER));
			books.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", Role.ROLE_USER));
			books.add(new Book("Head First Design Patterns", "Eric Freeman", Role.ROLE_PREMIUM));
			books.add(new Book("Refactoring", "Martin Fowler", Role.ROLE_USER));
			books.add(new Book("Domain-Driven Design", "Eric Evans", Role.ROLE_PREMIUM));
			books.add(new Book("Java Concurrency in Practice", "Brian Goetz", Role.ROLE_USER));
			books.add(new Book("Test-Driven Development", "Kent Beck", Role.ROLE_USER));
			
			List<User> users = new ArrayList();
			users.add(new User("Alice", "alice", "alice@example.com", passwordEncoder.encode("password"), Role.ROLE_LIBRANIAN));
			users.add(new User("Bob", "bob", "bob@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			users.add(new User("Charlie", "charlie", "charlie@example.com", passwordEncoder.encode("password"), Role.ROLE_PREMIUM));
			users.add(new User("Diana", "diana", "diana@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			users.add(new User("Eve", "eve", "eve@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			users.add(new User("Frank", "frank", "frank@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			users.add(new User("Grace", "grace", "grace@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			users.add(new User("Heidi", "heidi", "heidi@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			users.add(new User("Ivan", "ivan", "ivan@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			users.add(new User("Judy", "judy", "judy@example.com", passwordEncoder.encode("password"), Role.ROLE_USER));
			
			List<BorrowHistory> bhs = new ArrayList();
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,1,1, 5, 30), null));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,2,1, 5, 30), LocalDateTime.of(2023,2,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,3,1, 5, 30), LocalDateTime.of(2023,3,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,4,1, 5, 30), LocalDateTime.of(2023,4,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,5,1, 5, 30), LocalDateTime.of(2023,5,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,6,1, 5, 30), LocalDateTime.of(2023,6,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,7,1, 5, 30), LocalDateTime.of(2023,7,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,8,1, 5, 30), LocalDateTime.of(2023,8,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,9,1, 5, 30), LocalDateTime.of(2023,9,16, 18, 30)));
			bhs.add(new BorrowHistory(LocalDateTime.of(2023,10,1, 5, 30), LocalDateTime.of(2023,10,16, 18, 30)));
			
			for (int i = 0; i < bhs.size(); i++) {
				users.get(i).addBorrow(bhs.get(i));
				books.get(i).addBorrow(bhs.get(i));
				uRepo.save(users.get(i));
				bRepo.save(books.get(i));
				bhRepo.save(bhs.get(i));
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
