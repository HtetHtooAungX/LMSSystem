package com.hha.demo.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@NotEmpty(message = "Name cannot be empty!")
	private String name;
	
	@Column(nullable = false, unique = true)
	@NotEmpty(message = "Username cannot be empty!")
	private String username;
	
	@Column(nullable = false, unique = true)
	@NotEmpty(message = "Email cannot be empty!")
	private String email;
	
	@Column(nullable = false)
	@NotEmpty(message = "Password cannot be empty!")
	private String password;
	
	@Column(nullable = false)
	private Role role;
	
	public User(String name, String username, String email, String password, Role role) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<BorrowHistory> borrowHistory = new ArrayList<BorrowHistory>();
	
	public void addBorrow(BorrowHistory bh) {
		this.borrowHistory.add(bh);
		bh.setUser(this);
	}

	public enum Role {
		ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_LIBRANIAN, ROLE_USER, ROLE_PREMIUM
	}
}
