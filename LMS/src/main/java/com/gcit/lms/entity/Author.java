package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Author implements Serializable{

	private static final long serialVersionUID = 3086595661765643871L;

	private Integer authorId;
	
	private String authorName;
	
	private List<Book> books;

	/**
	 * @return the authorId
	 */
	public Integer getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param list the books to set
	 */
	public void setBooks(List<Book> list) {
		this.books = list;
	}
}
