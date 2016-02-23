package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{

	@Autowired
	BookDAO bookDAO;
	
	public void addGenre (Genre genre) throws SQLException {
		template.update("insert into tbl_genre(genre_name) values (?)", new Object[] {genre.getGenreName()} );
	}
	
	public void deleteGenre(Genre genre) throws SQLException {
		template.update("delete from tbl_book_genres where genre_id = ?", new Object[]{genre.getGenreId()});
		template.update("delete from tbl_genre where genre_id = ?", new Object[]{genre.getGenreId()});
	}
	
	public void updateGenre(Genre genre) throws SQLException {
		template.update("update tbl_genre set genre_name = ? where genre_id = ?", new Object[]{genre.getGenreName(), genre.getGenreId()});
	}
	
	public List<Genre> readAll() throws SQLException {
		return template.query("select * from tbl_genre", this);
	}

	public List<Genre> getGenreByID(String query, Object[] vals) throws SQLException {
		return template.query(query, vals, this);
	}
	
	public List<Genre> readAllGenresForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String like = "";
		
		
		if (searchString != null && !"".equals(searchString)) {
			like = " where genre_name like '%" + searchString + "%' ";
		}
		
		String query = "select * from tbl_genre";
		return template.query(addLimit(query + like), this);
	}
	
	public List<Genre> readAllGenresForPage(int pageNo, int pageSize) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String query = "select * from tbl_genre";
		return template.query(addLimit(query), this);
	}
	
	@Override
	public List<Genre> extractData(ResultSet rs) {
		List<Genre> list = new ArrayList<Genre>();
		try {
			while (rs.next()) {
				Genre genre = new Genre();
				genre.setGenreId(rs.getInt("genre_id"));
				genre.setGenreName(rs.getString("genre_name"));
				list.add(genre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Genre getGenreById(Integer genreId) {
		return template.query("select * from tbl_genre where genre_id = ?", new Object[]{genreId}, this).get(0);
	}
}
