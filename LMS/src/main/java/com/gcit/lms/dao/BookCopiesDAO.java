package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>>{

	public void addBookCopies(BookCopies bc) throws SQLException {
		template.update("insert into tbl_book_copies (bookId,branchId,noOfCpies) values( ? , ?, ?)",
				new Object[] { bc.getBookId(), bc.getBranchId(), bc.getNoOfCopies() });
	}
	
	public void updateBookCopies(BookCopies bc) throws SQLException {
		template.update("update tbl_book_copies set noOfCopies = ? where bookId = ? AND branchId = ?",  
				new Object[] {bc.getNoOfCopies(),bc.getBookId(), bc.getBranchId()});
	}
 	
	public List<BookCopies> readAll() throws SQLException {
		return template.query("select * from tbl_book_copies", this);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) {
		List<BookCopies> bookCopiesList = new ArrayList<BookCopies>();

		try {
			while (rs.next()) {
				BookCopies bookCopies = new BookCopies();
				bookCopies.setBranchId(rs.getInt("branchId"));
				bookCopies.setBookId(rs.getInt("bookId"));
				bookCopies.setNoOfCopies(rs.getInt("noOfCopies"));
				bookCopiesList.add(bookCopies);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookCopiesList;
	}

	public void createBookCopies(BookCopies bc) {
		Integer noOfCopies = 0;
		template.update("insert into tbl_book_copies (bookId, branchId, noOfCopies) values(?, ?, ?)",
				new Object[] { bc.getBookId(), bc.getBranchId(), noOfCopies});
	}

	public void deleteBookCopies(BookCopies bc) {
		template.update("delete from tbl_book_copies where bookId = ? AND branchId = ?",
				new Object[] { bc.getBookId(), bc.getBranchId()});
	}
}
