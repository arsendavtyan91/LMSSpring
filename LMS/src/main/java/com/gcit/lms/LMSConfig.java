package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookAuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookGenreDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdministratorService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

@Component
public class LMSConfig {
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/library";
	private String username = "root";
	private String password = "123456";
	
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);

		return ds;
	}
	
	@Bean
	public JdbcTemplate template() {
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());
		return template;
	}
	
	@Bean
	public AuthorDAO authorDAO() {
		AuthorDAO adao = new AuthorDAO();

		return adao;
	}
	
	@Bean
	public BookDAO bookDAO() {
		BookDAO bdao = new BookDAO();

		return bdao;
	}

	@Bean
	public LibraryBranchDAO libraryBranchDAO() {
		LibraryBranchDAO lbdao = new LibraryBranchDAO();

		return lbdao;
	}
	
	@Bean
	public GenreDAO genreDAO() {
		GenreDAO gdao = new GenreDAO();

		return gdao;
	}
	
	@Bean
	public PublisherDAO publisherDAO() {
		PublisherDAO pdao = new PublisherDAO();

		return pdao;
	}
	
	@Bean
	public BorrowerDAO borrowerDAO() {
		BorrowerDAO bdao = new BorrowerDAO();

		return bdao;
	}
	@Bean
	public BookLoansDAO bookLoansDAO() {
		BookLoansDAO pdao = new BookLoansDAO();

		return pdao;
	}
	
	@Bean
	public BookGenreDAO bookGenreDAO() {
		BookGenreDAO pdao = new BookGenreDAO();

		return pdao;
	}
	
	@Bean
	public BookCopiesDAO bookCopiesDAO() {
		BookCopiesDAO bcdao = new BookCopiesDAO();

		return bcdao;
	}

	@Bean
	public BookAuthorDAO bookAuthorDAO() {
		BookAuthorDAO badao = new BookAuthorDAO();

		return badao;
	}
	
	@Bean
	public AdministratorService adminService(){
		AdministratorService service = new AdministratorService();
		return service;
	}
	
	@Bean
	public BorrowerService borrowerService(){
		BorrowerService service = new BorrowerService();
		return service;
	}
	
	@Bean
	public LibrarianService librarianService(){
		LibrarianService service = new LibrarianService();
		return service;
	}
}
