package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable{

	private static final long serialVersionUID = -3634617597816686454L;

	private Integer bookId;
	
	private String title;
	
	private Integer pubId;
	
	private Publisher publisher;
	
	private List<Author> authors;

	private List<Genre> genres;
	
	private List<LibraryBranch> libraryBranches;
	
	/**
	 * @return the Library Branches
	 */
	public List<LibraryBranch> getLibraryBranches() {
		return libraryBranches;
	}

	public void setLibraryBranches(List<LibraryBranch> libraryBranches) {
		this.libraryBranches = libraryBranches;
	}
	
	/**
	 * @return the genres
	 */
	public List<Genre> getGenres() {
		return genres;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	
	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the pubId
	 */
	public Integer getPubId() {
		return pubId;
	}

	/**
	 * @param pubId the pubId to set
	 */
	public void setPubId(Integer pubId) {
		this.pubId = pubId;
	}

	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
}
