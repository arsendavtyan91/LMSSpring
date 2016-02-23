package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Book;

public class BookDAO extends BaseDAO<Book> implements
		ResultSetExtractor<List<Book>> {

	@Autowired
	AuthorDAO authorDAO;

	@Autowired
	GenreDAO genreDAO;

	@Autowired
	PublisherDAO publisherDAO;

	@Autowired
	LibraryBranchDAO libraryBranchDAO;

	public void addBook(Book book) throws SQLException {
		template.update("insert into tbl_book (title, pubId) values(?, ?)",
				new Object[] { book.getTitle(),
						book.getPublisher().getPublisherId() });
	}

	public Integer addBookWithId(Book book) throws SQLException {
		final String query = "insert into tbl_book (title, pubId) values(?, ?)";
		final String title = book.getTitle();
		final Integer pubId = book.getPubId();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(query,
						new String[] { "id" });
				ps.setString(1, title);
				ps.setInt(2, pubId);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void deleteBook(Book book) throws SQLException {
		template.update("delete from tbl_book_copies where bookId = ?",
				new Object[] { book.getBookId() });
		template.update("delete from tbl_book_loans where bookId = ?",
				new Object[] { book.getBookId() });
		template.update("delete from tbl_book_genres where bookId = ?",
				new Object[] { book.getBookId() });
		template.update("delete from tbl_book_authors where bookId = ?",
				new Object[] { book.getBookId() });
		template.update("delete from tbl_book where bookId = ?",
				new Object[] { book.getBookId() });
		
		
	}

	public List<Book> getBookByID(String query, Object[] vals)
			throws SQLException {
		return template.query(query, vals, this);
	}

	public void updateBook(Book book) throws SQLException {
		template.update("update tbl_book set title = ?, pubId = ? where bookId = ?",
				new Object[] { book.getTitle(),book.getPubId(), book.getBookId() });
	}

	public List<Book> readAll() throws SQLException {
		return template.query("select * from tbl_book", this);
	}
	
	public List<Book> readAllWithPubId(Integer pubId) throws SQLException {
		return template.query("select * from tbl_book where pubId = ?", new Object[]{pubId}, this);
	}

	
	public List<Book> readAllBooksForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		
		if (searchString != null && !"".equals(searchString)) {
			like = " where title like '%" + searchString + "%' ";
		}
		
		String query = "select * from tbl_book";
		return template.query(addLimit(query + like), this);
	}
	
	public List<Book> readAllBooksForPage(int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String query = "select * from tbl_book";
		return template.query(addLimit(query), this);
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) {
		List<Book> books = new ArrayList<Book>();
		try {

			while (rs.next()) {
				Book book = new Book();

				book.setPubId(rs.getInt("pubId"));
				book.setTitle(rs.getString("title"));
				book.setBookId(rs.getInt("bookId"));
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	public List<Book> getAllBooksNonRepeatingBooksFromLibraryBranch(
			Integer branchId, Integer cardNo) {
		List<Book> result = null;
		String query = "select tbl_book.bookId,tbl_book.title,tbl_book.pubId from tbl_book join tbl_book_copies"
				+ " on tbl_book_copies.bookId = tbl_book.bookId where tbl_book_copies.branchId = ?"
				+ " and tbl_book_copies.noOfCopies > 0 and tbl_book_copies.noOfCopies is not null and tbl_book.bookId not in (select tbl_book.bookId from tbl_book"
				+ " JOIN tbl_book_loans on tbl_book_loans.bookId = tbl_book.bookId"
				+ " and tbl_book_loans.branchId = ? and tbl_book_loans.cardNo = ?)";
		
		result = template.query(query, new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				Book book = new Book();
				
				book.setTitle(rs.getString("title"));
				book.setBookId(rs.getInt("bookId"));
				book.setPubId(rs.getInt("pubId"));
				return book;
			}
			
		}, new Object[]{branchId, branchId, cardNo});
		return result;
//		return template.query(query, new Object[]{branchId, branchId, cardNo}, this);
	}
	
	public List<Book> getAllBooksNonRepeatingBooksFromLibraryBranchForPage(
			Integer branchId, Integer cardNo, int pageNo, int pageSize) {
		List<Book> result = null;
		String query = "select tbl_book.bookId,tbl_book.title,tbl_book.pubId from tbl_book join tbl_book_copies"
				+ " on tbl_book_copies.bookId = tbl_book.bookId where tbl_book_copies.branchId = ?"
				+ " and tbl_book_copies.noOfCopies > 0 and tbl_book_copies.noOfCopies is not null and tbl_book.bookId not in (select tbl_book.bookId from tbl_book"
				+ " JOIN tbl_book_loans on tbl_book_loans.bookId = tbl_book.bookId"
				+ " and tbl_book_loans.branchId = ? and tbl_book_loans.cardNo = ?)";
		setPageNo(pageNo);
		setPageSize(pageSize);
		result = template.query(addLimit(query), new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				Book book = new Book();
				
				book.setTitle(rs.getString("title"));
				book.setBookId(rs.getInt("bookId"));
				book.setPubId(rs.getInt("pubId"));
				return book;
			}
			
		}, new Object[]{branchId, branchId, cardNo});
		return result;
	}
	public List<Book> getAllBooksNonRepeatingBooksFromLibraryBranchForSearch(
			Integer branchId, Integer cardNo, String searchString, int pageNo, int pageSize) {
		
		List<Book> result = null;
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		if (searchString != null && !"".equals(searchString)) {
			like = " title like '%" + searchString + "%' AND ";
		}
		
		String query = "select tbl_book.bookId,tbl_book.title,tbl_book.pubId from tbl_book join tbl_book_copies"
				+ " on tbl_book_copies.bookId = tbl_book.bookId where ";
		String query1 = "tbl_book_copies.branchId = ?"
				+ " and tbl_book_copies.noOfCopies > 0 and tbl_book_copies.noOfCopies is not null and tbl_book.bookId not in (select tbl_book.bookId from tbl_book"
				+ " JOIN tbl_book_loans on tbl_book_loans.bookId = tbl_book.bookId"
				+ " and tbl_book_loans.branchId = ? and tbl_book_loans.cardNo = ?)";
		
		result = template.query(addLimit(query + like + query1), new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				Book book = new Book();
				
				book.setTitle(rs.getString("title"));
				book.setBookId(rs.getInt("bookId"));
				book.setPubId(rs.getInt("pubId"));
				return book;
			}
			
		}, new Object[]{branchId, branchId, cardNo});
		return result;
	}

}
