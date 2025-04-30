package com.hha.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BookUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String author;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;
	
	private String fileType;

	public BookUpload(String name, String author, String image, String fileType) {
		this.name = name;
		this.author = author;
		this.image = image;
		this.fileType = fileType;
	}
}
