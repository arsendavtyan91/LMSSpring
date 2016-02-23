package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Genre implements Serializable {

	private static final long serialVersionUID = 7982686206406225512L;

	private Integer genreId;

	private String genreName;
	
	private List<Book> books;
	
	/**
	 * @return the genreId
	 */
	public Integer getGenreId() {
		return genreId;
	}

	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}

	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}

	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
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
