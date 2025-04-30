package com.hha.demo.dto;

import com.hha.demo.entity.BookUpload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookUploadDto {
	
	private int id;
	private String name;
	private String author;
	private String image;
	private String fileType;
	
	public static BookUpload fromDto(BookUploadDto uploadDto) {
		return new BookUpload(
				uploadDto.name,
				uploadDto.author,
				uploadDto.image,
				uploadDto.fileType
				);
	}

	public static BookUploadDto toDto(BookUpload upload, boolean detail) {
		return new BookUploadDto(
				upload.getId(),
				upload.getName(),
				upload.getAuthor(),
				detail ? upload.getImage() : "",
				upload.getFileType()
				);
	}
	
	

}
