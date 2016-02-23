package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{

	@Autowired
	BookDAO bookDAO;
	
	public void addPublisher(Publisher publisher) throws SQLException {
		template.update("insert into tbl_publisher(publisherName, publisherAddress, publisherPhone) values(?, ?, ?)", new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException {
		List<Book> books = bookDAO.readAllWithPubId(publisher.getPublisherId());
		for(Book b : books) {
				bookDAO.deleteBook(b);
		}
		template.update("delete from tbl_publisher where publisherId = ?", new Object[]{publisher.getPublisherId()});
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException {
		template.update("update tbl_publisher set publisherName = ? , publisherAddress = ?, publisherPhone = ? where publisherId = ?", new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()});
	}
	
	public List<Publisher> readAll() throws SQLException {
		return template.query("select * from tbl_publisher", this);
	}

	public List<Publisher> getPublisherByID(String query, Object[] vals) throws SQLException {
		return template.query(query, vals, this);
	}
	
	public List<Publisher> readAllPublishersForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		
		if (searchString != null && !"".equals(searchString)) {
			like = " where publisherName like '%" + searchString + "%' ";
		}
		
		String query = "select * from tbl_publisher";
		return template.query(addLimit(query + like), this);
	}
	
	public List<Publisher> readAllPublishersForPage(int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String query = "select * from tbl_publisher";
		return template.query(addLimit(query), this);
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) {
		List<Publisher> list = new ArrayList<Publisher>();
		try {
			while (rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setPublisherId(rs.getInt("publisherId"));
				publisher.setPublisherName(rs.getString("publisherName"));
				publisher.setPublisherAddress(rs.getString("publisherAddress"));
				publisher.setPublisherPhone(rs.getString("publisherPhone"));
				list.add(publisher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
