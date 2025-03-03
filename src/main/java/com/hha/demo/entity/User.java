package com.hha.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String address;
	@OneToMany(mappedBy = "user")
	private List<BorrowHistory> borrowHistory = new ArrayList<BorrowHistory>();
	
	public void addBorrow(BorrowHistory bh) {
		this.borrowHistory.add(bh);
		bh.setUser(this);
	}
}
