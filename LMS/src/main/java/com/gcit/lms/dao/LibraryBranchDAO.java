package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCount;
import com.gcit.lms.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements
		ResultSetExtractor<List<LibraryBranch>> {

	public void addLibraryBranch(LibraryBranch libraryBranch)
			throws SQLException {
		template.update(
				"insert into tbl_library_branch(branchName, branchAddress) values(?, ?)",
				new Object[] { libraryBranch.getBranchName(),
						libraryBranch.getBranchAddress() });
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch)
			throws SQLException {
		template.update("delete from tbl_book_loans where branchId = ?",
				new Object[] { libraryBranch.getBranchId() });
		template.update("delete from tbl_book_copies where branchId = ?",
				new Object[] { libraryBranch.getBranchId() });
		template.update("delete from tbl_library_branch where branchId = ?",
				new Object[] { libraryBranch.getBranchId() });
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch)
			throws SQLException {
		template.update(
				"update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { libraryBranch.getBranchName(),
						libraryBranch.getBranchAddress(),
						libraryBranch.getBranchId() });
	}

	public List<LibraryBranch> readAll() throws SQLException {
		return template.query("select * from tbl_library_branch", this);
	}

	public List<LibraryBranch> getLibraryBranchByID(String query, Object[] vals) throws SQLException {
		return template.query(query, vals, this);
	}
	
	public LibraryBranch getLibraryBranchFromId(Integer branchId) {
		return template.query("select * from tbl_library_branch where branchId = ?", new Object[]{branchId}, this).get(0);
	}
	
	public List<LibraryBranch> readAllBranchesForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		
		if (searchString != null && !"".equals(searchString)) {
			like = " where branchName like '%" + searchString + "%' ";
		}
		
		String query = "select * from tbl_library_branch";
		return template.query(addLimit(query + like), this);
	}
	
	public List<LibraryBranch> readAllBranchesForPage(int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String query = "select * from tbl_library_branch";
		return template.query(addLimit(query), this);
	}

	public List<BookCount> getBooksFromBranch(LibraryBranch libraryBranch)
			throws SQLException {
		List<BookCount> result = null;

		String query = "select bookId, title, noOfCopies from ((tbl_book_copies natural join tbl_library_branch) natural join tbl_book) where branchId = ?";
		result = template.query(query, new RowMapper<BookCount>() {

			@Override
			public BookCount mapRow(ResultSet rs, int rowNum) throws SQLException {
				BookCount bc = new BookCount();

				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));

				bc.setBook(b);
				bc.setNoOfCopies(Integer.parseInt(rs.getString("noOfCopies")));
				return bc;
			}
		}, new Object[] { libraryBranch.getBranchId() });

		return result;
	}

	public List<BookCount> getBooksFromBranchForPage(LibraryBranch libraryBranch, int pageNo, int pageSize)
			throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		List<BookCount> result = null;

		String query = "select bookId, title, noOfCopies from ((tbl_book_copies natural join tbl_library_branch) natural join tbl_book) where branchID = ?";
		result = template.query(addLimit(query), new RowMapper<BookCount>() {

			@Override
			public BookCount mapRow(ResultSet rs, int rowNum) throws SQLException {
				BookCount bc = new BookCount();

				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));

				bc.setBook(b);
				bc.setNoOfCopies(Integer.parseInt(rs.getString("noOfCopies")));
				return bc;
			}
		}, new Object[] { libraryBranch.getBranchId() });

		return result;
	}


	public List<BookCount> getBooksFromBranchForSearch(LibraryBranch libraryBranch, String searchString, int pageNo, int pageSize)
			throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		
		if (searchString != null && !"".equals(searchString)) {
			like = " AND title like '%" + searchString + "%' ";
		}
		
		List<BookCount> result = null;

		String query = "select bookId, title, noOfCopies from ((tbl_book_copies natural join tbl_library_branch) natural join tbl_book) where branchId = ?";
		result = template.query(addLimit(query + like), new RowMapper<BookCount>() {

			@Override
			public BookCount mapRow(ResultSet rs, int rowNum) throws SQLException {
				BookCount bc = new BookCount();

				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));

				bc.setBook(b);
				bc.setNoOfCopies(Integer.parseInt(rs.getString("noOfCopies")));
				return bc;
			}
		}, new Object[] { libraryBranch.getBranchId() });

		return result;
	}

	
	@Override
	public List<LibraryBranch> extractData(ResultSet rs) {
		List<LibraryBranch> list = new ArrayList<LibraryBranch>();
		try {
			while (rs.next()) {
				LibraryBranch libraryBranch = new LibraryBranch();
				libraryBranch.setBranchId(rs.getInt("branchId"));
				libraryBranch.setBranchName(rs.getString("branchName"));
				libraryBranch.setBranchAddress(rs.getString("branchAddress"));
				list.add(libraryBranch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
