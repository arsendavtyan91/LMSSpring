package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookAuthor;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookGenre;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class AdministratorService {

	@Autowired
	AuthorDAO authorDAO;

	@Autowired
	BookDAO bookDAO;

	@Autowired
	PublisherDAO publisherDAO;

	@Autowired
	GenreDAO genreDAO;

	@Autowired
	BorrowerDAO borrowerDAO;

	@Autowired
	LibraryBranchDAO libraryBranchDAO;

	@Autowired
	BookAuthorDAO bookAuthorDAO;

	@Autowired
	BookGenreDAO bookGenreDAO;

	@Autowired
	BookLoansDAO bookLoansDAO;
	
	@Autowired
	BookCopiesDAO bookCopiesDAO;

	/*
	 * ---------------------------Author--------------------------
	 */
	@Transactional
	public void addAuthor(Author author) throws SQLException {
		authorDAO.addAuthor(author);
	}
	
	@Transactional
	public Integer addBookWithId(Book book) throws SQLException {
		return bookDAO.addBookWithId(book);
	}
	
	@Transactional
	public Integer addAuthorWithId(Author author) throws SQLException {
	 return authorDAO.addAuthorWithId(author);
	}
	
	
	public List<Author> getAllAuthors() throws SQLException {
		List<Author> authors = authorDAO.readAll();
		for(Author a: authors){
			List<Book> b = bookDAO.getBookByID("select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)", new Object[] {a.getAuthorId()});
			a.setBooks(b);
		}
		
		return authors;
	}
	
	public Author getOneAuthor(Integer currentId) throws SQLException {
		Author res = null;
		List<Author> authors = getAllAuthors();
		for (Author a : authors) {
			if (a.getAuthorId() == currentId) {
				res = a;
				break;
			}
		}
		return res;
	}
	
	@Transactional
	public void editAuthor(Author author) throws SQLException {
		authorDAO.updateAuthor(author);
	}

	@Transactional
	public void deleteAuthor(Author author) throws SQLException {
		authorDAO.deleteAuthor(author);
	}
	
	@Transactional
	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		libraryBranchDAO.deleteLibraryBranch(libraryBranch);
	}

	@Transactional
	public void deleteBorrower(Borrower borrower) throws SQLException {
		borrowerDAO.deleteBorrower(borrower);
	}

	/*
	 * ----------------------------Book-------------------------------
	 */

	@Transactional
	public void addBook(Book book) throws SQLException {
		bookDAO.addBook(book);
	}
	
	public List<Book> getAllBooks() throws SQLException {
		List<Book> books = bookDAO.readAll();
		for(Book b: books){
			b.setAuthors(authorDAO.getAuthorByID("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] {b.getBookId()}));
			b.setGenres(genreDAO.getGenreByID("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId = ?)", new Object[] {b.getBookId()}));
			b.setPublisher(publisherDAO.getPublisherByID("select * from tbl_publisher where publisherId = ?", new Object[] {b.getPubId()}).get(0));
			b.setLibraryBranches(libraryBranchDAO.getLibraryBranchByID("select * from tbl_library_branch where branchId IN (select branchId from tbl_book_copies where bookId = ?)", new Object[] {b.getBookId()}));
		}
		return books;
	}

	@Transactional
	public void editBook(Book book) throws SQLException {
		bookDAO.updateBook(book);
	}

	@Transactional
	public void deleteBook(Book book) throws SQLException {
		bookDAO.deleteBook(book);
	}
	
	@Transactional
	public void deleteBookAuthor(Author author, Book book) throws SQLException {
		BookAuthor ba = new BookAuthor();
		
		ba.setAuthorId(author.getAuthorId());
		ba.setBookId(book.getBookId());
		
		bookAuthorDAO.deleteBookAuthor(ba);
	}
	
	@Transactional
	public void deleteBookGenre(Genre genre, Book book) throws SQLException {
		BookGenre bg = new BookGenre();
		
		bg.setGenreId(genre.getGenreId());
		bg.setBookId(book.getBookId());
		
		bookGenreDAO.deleteBookGenre(bg);
	}

	public Book getOneBook(Integer currentId) throws SQLException {
		Book currentBook = null;
		List<Book> books = getAllBooks();
		for (Book b : books) {
			if (b.getBookId() == currentId) {
				currentBook = b;
				break;
			}
		}
		return currentBook;
	}

	/*
	 * ----------------------------Publisher----------------------------
	 */
	@Transactional
	public void addPublisher(Publisher publisher) throws SQLException {
		publisherDAO.addPublisher(publisher);
	}

	public List<Publisher> getPublishers() throws SQLException {
		List<Publisher> publishers = publisherDAO.readAll();
		for(Publisher p: publishers){
			p.setBooks(bookDAO.getBookByID("select * from tbl_book where pubId in (select pubId from tbl_publisher where pubId = ?)", new Object[] {p.getPublisherId()}));
		}
		return publishers;
	}

	@Transactional
	public void editPublisher(Publisher publisher) throws SQLException {
		publisherDAO.updatePublisher(publisher);
	}
	
	@Transactional
	public void deletePublisher(Publisher publisher) throws SQLException {
		publisherDAO.deletePublisher(publisher);
	}

	public Publisher getOnePublisher(Integer currentId) throws SQLException {
		Publisher currentPublisher = null;
		List<Publisher> publishers = getPublishers();
		for (Publisher p : publishers) {
			if (p.getPublisherId() == currentId) {
				currentPublisher = p;
				break;
			}
		}
		return currentPublisher;
	}

	/*
	 * ---------------------------Genre----------------------------------
	 */
	@Transactional
	public void addGenre(Genre genre) throws SQLException {
		genreDAO.addGenre(genre);
	}

	public List<Genre> getGenres() throws SQLException {
		List<Genre> genres = genreDAO.readAll();
		for(Genre g: genres){
			g.setBooks(bookDAO.getBookByID("select * from tbl_book where bookId in (select bookId from tbl_book_genres where genre_id = ?)", new Object[] {g.getGenreId()}));
		}
		return genres;
	}
	
	@Transactional
	public void editGenre(Genre genre) throws SQLException {
		genreDAO.updateGenre(genre);
	}

	@Transactional
	public void deleteGenre(Genre genre) throws SQLException {
		genreDAO.deleteGenre(genre);
	}

	public Genre getOneGenre(Integer genreId) throws SQLException {
		return genreDAO.getGenreById(genreId);
	}
	
	/*
	 * ----------------------------Library Branch----------------------------
	 */

	@Transactional
	public void addLibraryBranch(LibraryBranch libraryBranch)
			throws SQLException {
		libraryBranchDAO.addLibraryBranch(libraryBranch);
	}

	@Transactional
	public List<LibraryBranch> getLibraryBranchs() throws SQLException {
		return libraryBranchDAO.readAll();
	}

	@Transactional
	public void editLibraryBranch(LibraryBranch libraryBranch)
			throws SQLException {
		libraryBranchDAO.updateLibraryBranch(libraryBranch);
	}

	public LibraryBranch getOneLibraryBranch(Integer branchId) throws SQLException {
		return libraryBranchDAO.getLibraryBranchFromId(branchId);
	}
	
	/*
	 * ----------------------------Borrower----------------------------
	 */

	@Transactional
	public void addBorrower(Borrower borrower) throws SQLException {
		borrowerDAO.addBorrower(borrower);
	}

	public List<Borrower> getBorrowers() throws SQLException {
		return borrowerDAO.readAll();
	}
	
	@Transactional
	public void editBorrower(Borrower borrower) throws SQLException {
		borrowerDAO.updateBorrower(borrower);
	}
	
	public Borrower getOneBorrower(Integer cardNo) throws SQLException {
		return borrowerDAO.getOneBorrowerByCardNo(cardNo);
	}

	/*
	 * ----------------------------BookAuthor----------------------------
	 */

	public List<BookAuthor> getBooksForAuthor() throws SQLException {
		return bookAuthorDAO.readAll();
	}

	@Transactional
	public void addBookAuthor(Author author, Book b) throws SQLException {
		BookAuthor ba = new BookAuthor();
		ba.setAuthorId(author.getAuthorId());
		ba.setBookId(b.getBookId());
		bookAuthorDAO.addBookAuthor(ba);
	}

	/*
	 * -------------------Book Genre------------------
	 */
	
	@Transactional
	public void addBookGenre(Genre genre, Book book) throws SQLException {
		BookGenre bg = new BookGenre();

		bg.setGenreId(genre.getGenreId());
		bg.setBookId(book.getBookId());

		bookGenreDAO.addBookGenre(bg);
	}

	@Transactional
	public void overrideDueDate(BookLoans bookLoans) throws SQLException {
		bookLoansDAO.updateBookLoans(bookLoans);
	}

	public List<BookLoans> getAllBookLoans() throws SQLException {
		return bookLoansDAO.getAllLoansWithMembers();
	}
	
	public List<BookLoans> getAllBookLoansForPage(int pageNo, int pageSize) throws SQLException {
		return bookLoansDAO.getAllLoansWithMembersForPage(pageNo, pageSize);
	}
	
	public List<BookLoans> getAllBookLoansForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		return bookLoansDAO.getAllLoansWithMembersForSearch(searchString, pageNo, pageSize);
	}
	
	@Transactional
	public void createBookCopies(Book book, LibraryBranch libraryBranch) {
		BookCopies bc = new BookCopies();
		bc.setBookId(book.getBookId());
		bc.setBranchId(libraryBranch.getBranchId());
		bookCopiesDAO.createBookCopies(bc);
	}

	@Transactional
	public void deleteBookCopies(LibraryBranch lb, Book currentBook) {
		BookCopies bc = new BookCopies();
		bc.setBranchId(lb.getBranchId());
		bc.setBookId(currentBook.getBookId());
		
		bookCopiesDAO.deleteBookCopies(bc);
	}
	/*
	 * Search
	 */

	public List<Author> getAllAuthorsForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		List<Author> authors = authorDAO.readAllForSearch(searchString, pageNo, pageSize);
		for(Author a: authors){
			List<Book> b = bookDAO.getBookByID("select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)", new Object[] {a.getAuthorId()});
			a.setBooks(b);
		}
		
		return authors;
	}
	
	public List<Author> getAllAuthorsForPageNo(int pageNo, int pageSize) throws SQLException {
		List<Author> authors = authorDAO.readAllForPage(pageNo, pageSize);
		for(Author a: authors){
			List<Book> b = bookDAO.getBookByID("select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)", new Object[] {a.getAuthorId()});
			a.setBooks(b);
		}
		return authors;
	}
	

	public List<Book> getAllBooksForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		List<Book> books = bookDAO.readAllBooksForSearch(searchString, pageNo, pageSize);
		for(Book b: books){
			b.setAuthors(authorDAO.getAuthorByID("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] {b.getBookId()}));
			b.setGenres(genreDAO.getGenreByID("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId = ?)", new Object[] {b.getBookId()}));
			b.setPublisher(publisherDAO.getPublisherByID("select * from tbl_publisher where publisherId = ?", new Object[] {b.getPubId()}).get(0));
			b.setLibraryBranches(libraryBranchDAO.getLibraryBranchByID("select * from tbl_library_branch where branchId IN (select branchId from tbl_book_copies where bookId = ?)", new Object[] {b.getBookId()}));
		}
		return books;
	}
	
	public List<Book> getAllBooksForPageNo(int pageNo, int pageSize) throws SQLException {
		List<Book> books = bookDAO.readAllBooksForPage(pageNo, pageSize);
		for(Book b: books){
			b.setAuthors(authorDAO.getAuthorByID("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] {b.getBookId()}));
			b.setGenres(genreDAO.getGenreByID("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId = ?)", new Object[] {b.getBookId()}));
			b.setPublisher(publisherDAO.getPublisherByID("select * from tbl_publisher where publisherId = ?", new Object[] {b.getPubId()}).get(0));
			b.setLibraryBranches(libraryBranchDAO.getLibraryBranchByID("select * from tbl_library_branch where branchId IN (select branchId from tbl_book_copies where bookId = ?)", new Object[] {b.getBookId()}));
		}
		return books;
	}
	
	public List<Genre> getAllGenresForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		List<Genre> genres = genreDAO.readAllGenresForSearch(searchString, pageNo, pageSize);
		for(Genre g: genres){
			g.setBooks(bookDAO.getBookByID("select * from tbl_book where bookId in (select bookId from tbl_book_genres where genre_id = ?)", new Object[] {g.getGenreId()}));
		}
		return genres;
	}
	
	public List<Genre> getAllGenresForPageNo(int pageNo, int pageSize) throws SQLException {
		List<Genre> genres = genreDAO.readAllGenresForPage(pageNo, pageSize);
		for(Genre g: genres){
			g.setBooks(bookDAO.getBookByID("select * from tbl_book where bookId in (select bookId from tbl_book_genres where genre_id = ?)", new Object[] {g.getGenreId()}));
		}
		return genres;
	}
	
	public List<Publisher> getAllPublishersForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		List<Publisher> publishers = publisherDAO.readAllPublishersForSearch(searchString, pageNo, pageSize);
		for(Publisher p: publishers){
			p.setBooks(bookDAO.getBookByID("select * from tbl_book where pubId in (select pubId from tbl_publisher where pubId = ?)", new Object[] {p.getPublisherId()}));
		}
		return publishers;
	}
	
	public List<Publisher> getAllPublishersForPageNo(int pageNo, int pageSize) throws SQLException {
		List<Publisher> publishers = publisherDAO.readAllPublishersForPage(pageNo, pageSize);
		for(Publisher p: publishers){
			p.setBooks(bookDAO.getBookByID("select * from tbl_book where pubId in (select pubId from tbl_publisher where pubId = ?)", new Object[] {p.getPublisherId()}));
		}
		return publishers;
	}
	
	public List<LibraryBranch> getAllBranchesForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.readAllBranchesForSearch(searchString, pageNo, pageSize);
	}
	
	public List<LibraryBranch> getAllBranchesForPageNo(int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.readAllBranchesForPage(pageNo, pageSize);
	}
	
	public List<Borrower> getAllBorrowersForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		return borrowerDAO.readAllBorrowersForSearch(searchString, pageNo, pageSize);
	}
	
	public List<Borrower> getAllBorrowersForPageNo(int pageNo, int pageSize) throws SQLException {
		return borrowerDAO.readAllForPage(pageNo, pageSize);
	}
	
	public String encodeString(String str) {
		return str.
				replace("&", "&amp;").
				replace(">", "&gt;").
				replace("<", "&lt;").
				replace("@", "&commat;").
				replace("(", "&lpar;").
				replace(")", "&rpar;").
				replace("=", "&equals;").
				replace("{", "&lcub; ").
				replace("}", "&rcub;").
				replace(",", "&comma;").
				replace(".", "&period;");
	}
}
