package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

public class BookLoansDAO extends BaseDAO<BookLoans> implements
		ResultSetExtractor<List<BookLoans>> {

	public void addBookLoans(BookLoans book) throws SQLException {
		template.update(
				"insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values(?, ?, ?, ? , ?, ?)",
				new Object[] { book.getBook().getBookId(),
						book.getLibraryBranch().getBranchId(),
						book.getBorrower().getCardNo(), book.getDateOut(),
						book.getDueDate(), book.getDateIn() });
		
		template.update("UPDATE tbl_book_copies SET noOfCOpies = noOfCopies - 1 WHERE bookId = ? AND branchId = ?", 
				new Object[]{book.getBook().getBookId(), book.getLibraryBranch().getBranchId()});
	}

	public void deleteBookLoans(BookLoans book) throws SQLException {
		template.update(
				"delete from tbl_book_loans where bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { book.getBook().getBookId(),
						book.getLibraryBranch().getBranchId(),
						book.getBorrower().getCardNo() });
		template.update("UPDATE tbl_book_copies SET noOfCOpies = noOfCopies + 1 WHERE bookId = ? AND branchId = ?", 
				new Object[]{book.getBook().getBookId(), book.getLibraryBranch().getBranchId()});
	}

	public void updateBookLoans(BookLoans book) throws SQLException {
		template.update(
				"update tbl_book_loans set dueDate = ? where bookId = ? AND cardNo = ? AND branchId = ?",
				new Object[] { book.getDueDate(), book.getBook().getBookId(),
						book.getBorrower().getCardNo(),
						book.getLibraryBranch().getBranchId() });
	}

	public List<BookLoans> readAll() throws SQLException {
		return template.query("select * from tbl_book_loans", this);
	}

	public List<BookLoans> getAllLoansWithMembers() throws SQLException {
		List<BookLoans> result = null;
		String query = "select tbl_book_loans.cardNo, name, tbl_book_loans.bookId, title, tbl_book_loans.branchId, branchName, branchAddress, dueDate  from tbl_book_loans "
				+ "join tbl_book on tbl_book_loans.bookId = tbl_book.bookId "
				+ "join tbl_library_branch on tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ "join tbl_borrower on tbl_book_loans.cardNo = tbl_borrower.cardNo ";

		result = template.query(query, new RowMapper<BookLoans>() {
			public BookLoans mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BookLoans bookLoans = new BookLoans();
				Book book = new Book();
				LibraryBranch libraryBranch = new LibraryBranch();
				Borrower borrower = new Borrower();

				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));

				libraryBranch.setBranchId(rs.getInt("branchId"));
				libraryBranch.setBranchName(rs.getString("branchName"));
				libraryBranch.setBranchAddress(rs.getString("branchAddress"));

				borrower.setCardNo(rs.getInt("cardNo"));
				borrower.setName(rs.getString("name"));

				bookLoans.setDueDate(rs.getDate("dueDate"));

				bookLoans.setBook(book);
				bookLoans.setLibraryBranch(libraryBranch);
				bookLoans.setBorrower(borrower);

				return bookLoans;
			}
		});
		return result;

	}

	public List<BookLoans> getAllLoansWithMembersForPage(int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		List<BookLoans> result = null;
		String query = "select tbl_book_loans.cardNo, name, tbl_book_loans.bookId, title, tbl_book_loans.branchId, branchName, branchAddress, dueDate  from tbl_book_loans "
				+ "join tbl_book on tbl_book_loans.bookId = tbl_book.bookId "
				+ "join tbl_library_branch on tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ "join tbl_borrower on tbl_book_loans.cardNo = tbl_borrower.cardNo ";

		result = template.query(addLimit(query), new RowMapper<BookLoans>() {
			public BookLoans mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BookLoans bookLoans = new BookLoans();
				Book book = new Book();
				LibraryBranch libraryBranch = new LibraryBranch();
				Borrower borrower = new Borrower();

				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));

				libraryBranch.setBranchId(rs.getInt("branchId"));
				libraryBranch.setBranchName(rs.getString("branchName"));
				libraryBranch.setBranchAddress(rs.getString("branchAddress"));

				borrower.setCardNo(rs.getInt("cardNo"));
				borrower.setName(rs.getString("name"));

				bookLoans.setDueDate(rs.getDate("dueDate"));

				bookLoans.setBook(book);
				bookLoans.setLibraryBranch(libraryBranch);
				bookLoans.setBorrower(borrower);

				return bookLoans;
			}
		});
		return result;
	}

	public List<BookLoans> getAllLoansWithMembersForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		if (searchString != null && !"".equals(searchString)) {
			like = " where title like '%" + searchString + "%' or name like " + "'%" + searchString + "%' ";
		}
		
		List<BookLoans> result = null;
		String query = "select tbl_book_loans.cardNo, name, tbl_book_loans.bookId, title, tbl_book_loans.branchId, branchName, branchAddress, dueDate  from tbl_book_loans "
				+ "join tbl_book on tbl_book_loans.bookId = tbl_book.bookId "
				+ "join tbl_library_branch on tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ "join tbl_borrower on tbl_book_loans.cardNo = tbl_borrower.cardNo ";

		result = template.query(addLimit(query + like), new RowMapper<BookLoans>() {
			public BookLoans mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BookLoans bookLoans = new BookLoans();
				Book book = new Book();
				LibraryBranch libraryBranch = new LibraryBranch();
				Borrower borrower = new Borrower();

				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));

				libraryBranch.setBranchId(rs.getInt("branchId"));
				libraryBranch.setBranchName(rs.getString("branchName"));
				libraryBranch.setBranchAddress(rs.getString("branchAddress"));

				borrower.setCardNo(rs.getInt("cardNo"));
				borrower.setName(rs.getString("name"));

				bookLoans.setDueDate(rs.getDate("dueDate"));

				bookLoans.setBook(book);
				bookLoans.setLibraryBranch(libraryBranch);
				bookLoans.setBorrower(borrower);

				return bookLoans;
			}
		});
		return result;
	}

	public List<BookLoans> getAllLoansForBorrower(Borrower borrower)
			throws SQLException {
		List<BookLoans> result = new ArrayList<BookLoans>();

		String query = " select tbl_book_loans.bookId, title, tbl_book_loans.branchId, branchName, branchAddress, dueDate  from tbl_book_loans "
				+ " join tbl_book on tbl_book_loans.bookId = tbl_book.bookId "
				+ " join tbl_library_branch on tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ " join tbl_borrower on tbl_book_loans.cardNo = tbl_borrower.cardNo "
				+ " where tbl_book_loans.cardNo=?";

		
		
		result = template.query(query, new RowMapper<BookLoans>() {

			@Override
			public BookLoans mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BookLoans bookLoans = new BookLoans();
				Book book = new Book();
				LibraryBranch libraryBranch = new LibraryBranch();

				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));

				libraryBranch.setBranchId(rs.getInt("branchId"));
				libraryBranch.setBranchName(rs.getString("branchName"));
				libraryBranch.setBranchAddress(rs.getString("branchAddress"));

				bookLoans.setDueDate(rs.getDate("dueDate"));
				bookLoans.setBook(book);
				bookLoans.setLibraryBranch(libraryBranch);
				return bookLoans;

			}

		}, borrower.getCardNo());
		
		
		return result;
	}
	public List<BookLoans> getAllLoansForBorrowerForPage(Borrower borrower, int pageNo, int pageSize)
			throws SQLException {
		List<BookLoans> result = new ArrayList<BookLoans>();

		String query = " select tbl_book_loans.bookId, title, tbl_book_loans.branchId, branchName, branchAddress, dueDate  from tbl_book_loans "
				+ " join tbl_book on tbl_book_loans.bookId = tbl_book.bookId "
				+ " join tbl_library_branch on tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ " join tbl_borrower on tbl_book_loans.cardNo = tbl_borrower.cardNo "
				+ " where tbl_book_loans.cardNo=?";
		setPageNo(pageNo);
		setPageSize(pageSize);
		
		result = template.query(addLimit(query), new RowMapper<BookLoans>() {

			@Override
			public BookLoans mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BookLoans bookLoans = new BookLoans();
				Book book = new Book();
				LibraryBranch libraryBranch = new LibraryBranch();

				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));

				libraryBranch.setBranchId(rs.getInt("branchId"));
				libraryBranch.setBranchName(rs.getString("branchName"));
				libraryBranch.setBranchAddress(rs.getString("branchAddress"));

				bookLoans.setDueDate(rs.getDate("dueDate"));
				bookLoans.setBook(book);
				bookLoans.setLibraryBranch(libraryBranch);
				return bookLoans;

			}

		}, borrower.getCardNo());
		
		
		return result;
	}

	public List<BookLoans> getAllLoansForBorrowerForSearch(Borrower borrower, String searchString, int pageNo, int pageSize)
			throws SQLException {
		List<BookLoans> result = new ArrayList<BookLoans>();

		String query = " select tbl_book_loans.bookId, title, tbl_book_loans.branchId, branchName, branchAddress, dueDate  from tbl_book_loans "
				+ " join tbl_book on tbl_book_loans.bookId = tbl_book.bookId "
				+ " join tbl_library_branch on tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ " join tbl_borrower on tbl_book_loans.cardNo = tbl_borrower.cardNo "
				+ " where tbl_book_loans.cardNo=?";
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		if (searchString != null && !"".equals(searchString)) {
			like = " AND title like '%" + searchString + "%'";
		}
		
		result = template.query(addLimit(query + like), new RowMapper<BookLoans>() {

			@Override
			public BookLoans mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BookLoans bookLoans = new BookLoans();
				Book book = new Book();
				LibraryBranch libraryBranch = new LibraryBranch();

				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));

				libraryBranch.setBranchId(rs.getInt("branchId"));
				libraryBranch.setBranchName(rs.getString("branchName"));
				libraryBranch.setBranchAddress(rs.getString("branchAddress"));

				bookLoans.setDueDate(rs.getDate("dueDate"));
				bookLoans.setBook(book);
				bookLoans.setLibraryBranch(libraryBranch);
				return bookLoans;

			}

		}, borrower.getCardNo());
		
		
		return result;
	}

	
	@Override
	public List<BookLoans> extractData(ResultSet rs) {
		List<BookLoans> bookLoans = new ArrayList<BookLoans>();
		try {
			while (rs.next()) {
				BookLoans bl = new BookLoans();

				Book b = new Book();
				LibraryBranch lb = new LibraryBranch();
				Borrower borrower = new Borrower();
				lb.setBranchId(rs.getInt("branchId"));
				b.setBookId(rs.getInt("bookId"));
				borrower.setCardNo(rs.getInt("cardNo"));

				bl.setBook(b);
				bl.setLibraryBranch(lb);
				bl.setBorrower(borrower);
				bl.setDateIn(rs.getDate("dateIn"));
				bl.setDueDate(rs.getDate("dueDate"));
				bl.setDateOut(rs.getDate("dateOut"));
				bookLoans.add(bl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookLoans;
	}
}
