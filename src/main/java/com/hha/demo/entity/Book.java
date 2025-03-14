package com.hha.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.hha.demo.entity.User.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 5, max = 80)
	private String name;
	
	@NotEmpty(message = "Author cannot be empty")
	private String author;
	private Role visiable;
	
	public Book(String name, String author, Role visiable) {
		this.name = name;
		this.author = author;
		this.visiable = visiable;
	}
	
	@OneToMany(mappedBy = "book", orphanRemoval = true)
	private List<BorrowHistory> borrowHistory = new ArrayList<BorrowHistory>();
	
	public void addBorrow(BorrowHistory bh) {
		this.borrowHistory.add(bh);
		bh.setBook(this);
	}

}
