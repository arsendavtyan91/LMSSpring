package com.gcit.lms.entity;

import java.io.Serializable;

public class BookGenre implements Serializable {

	private static final long serialVersionUID = -3144151174268555059L;

	private Integer bookId;
	
	private Integer genreId;
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getGenreId() {
		return genreId;
	}

	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
}
