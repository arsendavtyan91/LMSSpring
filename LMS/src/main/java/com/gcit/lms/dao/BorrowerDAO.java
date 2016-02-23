package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{

	public void addBorrower(Borrower borrower) throws SQLException {
		template.update("insert into tbl_borrower(name, address, phone) values(?, ?, ?)", new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone()});
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException {
		template.update("delete from tbl_book_loans where cardNo = ?", new Object[]{borrower.getCardNo()});
		template.update("delete from tbl_borrower where cardNo = ?", new Object[]{borrower.getCardNo()});
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException {
		template.update("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?", new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()});
	}
	
	public List<Borrower> readAll() throws SQLException {
		return template.query("select * from tbl_borrower", this);
	}

	public List<Borrower> readAllBorrowersForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		if (searchString != null && !"".equals(searchString)) {
			like = " where name like '%" + searchString + "%' ";
		}
		
		String query = "select * from tbl_borrower";
		return template.query(addLimit(query + like), this);
	}
	
	public List<Borrower> readAllForPage(int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String query = "select * from tbl_borrower";
		return template.query(addLimit(query), this);
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) {
		List<Borrower> list = new ArrayList<Borrower>();
		try {
			while (rs.next()) {
				Borrower borrower = new Borrower();
				borrower.setCardNo(rs.getInt("cardNo"));
				borrower.setName(rs.getString("name"));
				borrower.setAddress(rs.getString("address"));
				borrower.setPhone(rs.getString("phone"));
				list.add(borrower);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Borrower getOneBorrowerByCardNo(Integer cardNo) throws SQLException {
		return template.query("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo}, this).get(0);
	}
	
	public List<Borrower> getBorrowerByCardNo(Integer cardNo) throws SQLException {
		return template.query("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo}, this);
	}
}
