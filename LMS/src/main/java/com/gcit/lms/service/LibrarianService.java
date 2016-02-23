package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookCount;
import com.gcit.lms.entity.LibraryBranch;

public class LibrarianService {

	@Autowired
	LibraryBranchDAO libraryBranchDAO;
	
	@Autowired
	BookCopiesDAO bookCopiesDAO;
	/*
	 * ---------------------Library Branch---------------------
	 */

	public LibraryBranch getBranchFromId (Integer branchId) {
		return libraryBranchDAO.getLibraryBranchFromId(branchId);
	}
	
	public List<LibraryBranch> getAllLibraryBranches() throws SQLException {
			return libraryBranchDAO.readAll();
	}

	@Transactional
	public void editLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		libraryBranchDAO.updateLibraryBranch(libraryBranch);
	}
	
	public List<BookCount> getBooksFromBranch(LibraryBranch libraryBranch) throws SQLException {
			return libraryBranchDAO.getBooksFromBranch(libraryBranch);
	}
	
	@Transactional
	public void updateBookCopies(BookCopies bc) throws SQLException {
		bookCopiesDAO.updateBookCopies(bc);
	}
	
	public List<LibraryBranch> getAllBranchesForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.readAllBranchesForSearch(searchString, pageNo, pageSize);
	}
	
	public List<LibraryBranch> getAllBranchesForPageNo(int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.readAllBranchesForPage(pageNo, pageSize);
	}
	
	public List<BookCount> getBooksFromBranchForPage(LibraryBranch libraryBranch, int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.getBooksFromBranchForPage(libraryBranch, pageNo, pageSize);
	}
	
	public List<BookCount> getBooksFromBranchForSearch(LibraryBranch libraryBranch, String searchString, int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.getBooksFromBranchForSearch(libraryBranch, searchString, pageNo, pageSize);
	}
}
