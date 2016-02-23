package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookAuthor;

public class BookAuthorDAO extends BaseDAO<BookAuthor> implements ResultSetExtractor<List<BookAuthor>>{

	public void addBookAuthor(BookAuthor bookauthor) throws SQLException {
		template.update("insert into tbl_book_authors (bookId, authorId) values(?, ?)", new Object[]{bookauthor.getBookId(), bookauthor.getAuthorId()});
	}
	
	public void deleteBookAuthor(BookAuthor bookauthor) throws SQLException {
		template.update("delete from tbl_book_authors where bookId = ? AND authorId = ?", new Object[]{bookauthor.getBookId(), bookauthor.getAuthorId()});
	}
	
	public List<BookAuthor> readAll() throws SQLException {
		return template.query("select * from tbl_book_author", this);
	}	

	@Override
	public List<BookAuthor> extractData(ResultSet rs) {
		return null;
	}
}
