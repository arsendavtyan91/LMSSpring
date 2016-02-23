package com.gcit.lms.entity;

import java.io.Serializable;

public class BookAuthor implements Serializable{

	private static final long serialVersionUID = -5071641249694637538L;

	private Integer bookId;
	
	private Integer authorId;
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
}
