package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookGenre;

public class BookGenreDAO extends BaseDAO<BookGenre> implements ResultSetExtractor<List<BookGenre>>{

	
	public void addBookGenre(BookGenre bookgenre) throws SQLException {
		template.update("insert into tbl_book_genres(bookId, genre_id) values(?, ?)", new Object[]{bookgenre.getBookId(), bookgenre.getGenreId()});
	}
	
	@Override
	public List<BookGenre> extractData(ResultSet rs) {
		return null;
	}

	public void deleteBookGenre(BookGenre bg) {
		template.update("delete from tbl_book_genres where bookId = ? AND genre_id = ?", new Object[]{bg.getBookId(), bg.getGenreId()});
	}
}
