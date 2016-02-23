package com.gcit.lms.entity;

import java.io.Serializable;

public class BookCount implements Serializable {
	
	private static final long serialVersionUID = 5534446129273243837L;

	private Integer noOfCopies;
	
	private Book book;
	
	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
