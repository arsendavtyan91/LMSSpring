package com.gcit.lms.entity;

import java.io.Serializable;

public class BookCopies implements Serializable{
	
	private static final long serialVersionUID = 424113854315355290L;

	private Integer bookId;
	
	private Integer branchId;
	
	private Integer noOfCopies;
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}
