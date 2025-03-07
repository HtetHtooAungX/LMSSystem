package com.hha.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	private String name;
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
