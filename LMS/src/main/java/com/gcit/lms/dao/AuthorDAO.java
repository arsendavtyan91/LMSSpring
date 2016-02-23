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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.entity.Author;

public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{
	
	@Autowired
	BookDAO bookDAO;
	
	@Transactional
	public void addAuthor(Author author) throws SQLException {
		template.update("insert into tbl_author (authorName) values(?)", new Object[]{author.getAuthorName()});
	}
	
	public List<Author> getAuthorByID(String query, Object[] vals) throws SQLException {
		return template.query(query, vals, this);
	}
	
	public Integer addAuthorWithId(Author author) throws SQLException {
		final String query = "insert into tbl_author (authorName) values(?)";
		final String authorName = author.getAuthorName();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(query, new String[] {"id"});
				ps.setString(1, authorName);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public void deleteAuthor(Author author) throws SQLException {
		template.update("delete from tbl_book_authors where authorId = ?", new Object[]{author.getAuthorId()});
		template.update("delete from tbl_author where authorId = ?", new Object[]{author.getAuthorId()});
	}
	
	public Author getOneAuthor(Integer currentId) {
		List<Author> authors = template.query("select * from tbl_author where authorId = ?", new Object[]{currentId}, this);
		return authors.get(0);
		
	}
	
	public void updateAuthor(Author author) throws SQLException {
		template.update("update tbl_author set authorName = ? where authorId = ?", new Object[]{author.getAuthorName(), author.getAuthorId()});
	}

	public List<Author> readAll() throws SQLException {
		String query = "select * from tbl_author";
		return template.query(query, this);
	}
	
	public List<Author> readAllForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		
		if (searchString != null && !"".equals(searchString)) {
			like = " where authorName like '%" + searchString + "%' ";
		}
		
		String query = "select * from tbl_author";
		return template.query(addLimit(query + like), this);
	}
	
	public List<Author> readAllForPage(int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String query = "select * from tbl_author";
		return template.query(addLimit(query), this);
	}
	
	public String getAuthorNameById(Integer authorId) throws SQLException {
		String query = "select * from tbl_author where authorId = ?";
		List<Author> authors = template.query(query, new Object[]{authorId}, this);
		return authors.get(0).getAuthorName();
	}
	
	@Override
	public List<Author> extractData(ResultSet rs) {
		List<Author> authors = new ArrayList<Author>();
		try {
			while(rs.next()){
				Author author = new Author();
				author.setAuthorId(rs.getInt("authorId"));
				author.setAuthorName(rs.getString("authorName"));
				authors.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}
}
