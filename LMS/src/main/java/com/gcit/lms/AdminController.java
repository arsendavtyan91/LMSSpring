package com.gcit.lms;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdministratorService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AdminController {

	@Autowired
	AdministratorService adminService;

	@RequestMapping(value = "/")
	public String home() {

		return "index";
	}

	/*
	 * ----------------------------Main Page-------------------------
	 */

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "main/admin";
	}

	@RequestMapping(value = "/librarian", method = RequestMethod.GET)
	public String librarian() {
		return "main/librarian";
	}

	@RequestMapping(value = "/borrower", method = RequestMethod.GET)
	public String borrower() {
		return "main/borrower";
	}

	@RequestMapping(value = "/borrowerModal", method = RequestMethod.GET)
	public String borrowerModal() {
		return "main/borrowerModal";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact() {
		return "contact";
	}

	/*
	 * ----------------------Administrator Functions-------------------------
	 */
	@RequestMapping(value = "/admin_authors", method = RequestMethod.GET)
	public String adminAuthors() {
		return "admin/viewAuthors";
	}

	@RequestMapping(value = "/admin_books", method = RequestMethod.GET)
	public String adminBooks() {
		return "admin/viewBooks";
	}

	@RequestMapping(value = "/admin_publishers", method = RequestMethod.GET)
	public String adminPublisher() {
		return "admin/viewPublishers";
	}

	@RequestMapping(value = "/admin_librarybranches", method = RequestMethod.GET)
	public String adminLibraryBranches() {
		return "admin/viewLibraryBranches";
	}

	@RequestMapping(value = "/admin_genres", method = RequestMethod.GET)
	public String adminGenres() {
		return "admin/viewGenres";
	}

	@RequestMapping(value = "/admin_borrowers", method = RequestMethod.GET)
	public String adminBorrowers() {
		return "admin/viewBorrowers";
	}

	@RequestMapping(value = "/admin_override", method = RequestMethod.GET)
	public String adminOverride() {
		return "admin/admin_override";
	}

	/*
	 * -------------------Publisher--------------------
	 */

	@RequestMapping(value = "/addPublisher", method = RequestMethod.GET)
	public String addPublisher() {
		return "admin/addPublisher";
	}

	@RequestMapping(value = "/viewPublishers", method = RequestMethod.GET)
	public String viewPublishers() {
		return "admin/viewPublishers";
	}

	@RequestMapping(value = "/addPublisherSubmit", method = RequestMethod.POST)
	public String addPublisherSubmit(
			@RequestParam("publisherName") String publisherName,
			@RequestParam("publisherAddress") String publisherAddress,
			@RequestParam("publisherPhone") String publisherPhone)
			throws SQLException {
		Publisher publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);

		adminService.addPublisher(publisher);
		return "admin/viewPublishers";
	}

	@RequestMapping(value = "/editPublisher", method = RequestMethod.GET)
	public String editPublisher(@RequestParam("publisherId") Integer publisherId) {
		return "admin/editPublisher";
	}

	@RequestMapping(value = "/deletePublisher", method = RequestMethod.GET)
	public String deletePublisher(
			@RequestParam("publisherId") Integer publisherId)
			throws SQLException {
		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		adminService.deletePublisher(publisher);
		return "admin/viewPublishers";
	}

	@RequestMapping(value = "/editPublisherSubmit", method = RequestMethod.POST)
	public String editPublisherSubmit(
			@RequestParam("publisherId") Integer publisherId,
			@RequestParam("publisherName") String publisherName,
			@RequestParam("publisherAddress") String publisherAddress,
			@RequestParam("publisherPhone") String publisherPhone)
			throws SQLException {
		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);
		adminService.editPublisher(publisher);
		return "admin/viewPublishers";
	}

	/*
	 * -------------------Genre--------------------
	 */

	@RequestMapping(value = "/addGenre", method = RequestMethod.GET)
	public String addGenre() {
		return "admin/addGenre";
	}

	@RequestMapping(value = "/viewGenres", method = RequestMethod.GET)
	public String viewGenres() {
		return "admin/viewGenres";
	}

	@RequestMapping(value = "/addGenreSubmit", method = RequestMethod.POST)
	public String addGenreSubmit(@RequestParam("genreName") String genreName)
			throws SQLException {
		Genre genre = new Genre();
		genre.setGenreName(genreName);

		adminService.addGenre(genre);
		return "admin/viewGenres";
	}

	@RequestMapping(value = "/editGenre", method = RequestMethod.GET)
	public String editGenre(@RequestParam("genreId") Integer genreId) {
		return "admin/editGenre";
	}

	@RequestMapping(value = "/deleteGenre", method = RequestMethod.GET)
	public String deleteGenre(@RequestParam("genreId") Integer genreId)
			throws SQLException {
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		adminService.deleteGenre(genre);
		return "admin/viewGenres";
	}

	@RequestMapping(value = "/editGenreSubmit", method = RequestMethod.POST)
	public String editGenreSubmit(@RequestParam("genreId") Integer genreId,
			@RequestParam("genreName") String genreName) throws SQLException {
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		genre.setGenreName(genreName);
		adminService.editGenre(genre);
		return "admin/viewGenres";
	}

	/*
	 * -------------------Library Branch--------------------
	 */

	@RequestMapping(value = "/addLibraryBranch", method = RequestMethod.GET)
	public String addLibraryBranch() {
		return "admin/addLibraryBranch";
	}

	@RequestMapping(value = "/viewLibraryBranches", method = RequestMethod.GET)
	public String viewLibraryBranches() {
		return "admin/viewLibraryBranches";
	}

	@RequestMapping(value = "/addLibraryBranchSubmit", method = RequestMethod.POST)
	public String addLibraryBranchSubmit(
			@RequestParam("branchName") String branchName,
			@RequestParam("branchAddress") String branchAddress)
			throws SQLException {
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchName(branchName);
		lb.setBranchAddress(branchAddress);
		adminService.addLibraryBranch(lb);
		return "admin/viewLibraryBranches";
	}

	@RequestMapping(value = "/adminEditLibraryBranch", method = RequestMethod.GET)
	public String editLibraryBranch(@RequestParam("branchId") Integer branchId) {
		return "admin/adminEditLibraryBranch";
	}

	@RequestMapping(value = "/deleteLibraryBranch", method = RequestMethod.GET)
	public String deleteLibraryBranch(@RequestParam("branchId") Integer branchId)
			throws SQLException {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setBranchId(branchId);
		adminService.deleteLibraryBranch(libraryBranch);
		return "admin/viewLibraryBranches";
	}

	@RequestMapping(value = "/adminEditLibraryBranchSubmit", method = RequestMethod.POST)
	public String editLibraryBranchSubmit(
			@RequestParam("branchId") Integer branchId,
			@RequestParam("branchName") String branchName,
			@RequestParam("branchAddress") String branchAddress)
			throws SQLException {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setBranchId(branchId);
		libraryBranch.setBranchName(branchName);
		libraryBranch.setBranchAddress(branchAddress);
		adminService.editLibraryBranch(libraryBranch);
		return "admin/viewLibraryBranches";
	}

	/*
	 * -------------------Borrower--------------------
	 */

	@RequestMapping(value = "/addBorrower", method = RequestMethod.GET)
	public String addBorrower() {
		return "admin/addBorrower";
	}

	@RequestMapping(value = "/viewBorrowers", method = RequestMethod.GET)
	public String viewBorrowers() {
		return "admin/viewBorrowers";
	}

	@RequestMapping(value = "/addBorrowerSubmit", method = RequestMethod.POST)
	public String addBorrowerSubmit(
			@RequestParam("borrowerName") String borrowerName,
			@RequestParam("borrowerAddress") String borrowerAddress,
			@RequestParam("borrowerPhone") String borrowerPhone)
			throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setName(borrowerName);
		borrower.setAddress(borrowerAddress);
		borrower.setPhone(borrowerPhone);

		adminService.addBorrower(borrower);
		return "admin/viewBorrowers";
	}

	@RequestMapping(value = "/editBorrower", method = RequestMethod.GET)
	public String editBorrower(@RequestParam("cardNo") Integer cardNo) {
		return "admin/editBorrower";
	}

	@RequestMapping(value = "/deleteBorrower", method = RequestMethod.GET)
	public String deleteBorrower(@RequestParam("cardNo") Integer cardNo)
			throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		adminService.deleteBorrower(borrower);
		return "admin/viewBorrowers";
	}

	@RequestMapping(value = "/editBorrowerSubmit", method = RequestMethod.POST)
	public String editBorrowerSubmit(@RequestParam("cardNo") Integer cardNo,
			@RequestParam("borrowerName") String borrowerName,
			@RequestParam("borrowerAddress") String borrowerAddress,
			@RequestParam("borrowerPhone") String borrowerPhone)
			throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		borrower.setName(borrowerName);
		borrower.setAddress(borrowerAddress);
		borrower.setPhone(borrowerPhone);
		adminService.editBorrower(borrower);
		return "admin/viewBorrowers";
	}

	/*
	 * -------------------Author--------------------
	 */

	@RequestMapping(value = "/addAuthor", method = RequestMethod.GET)
	public String addAuthor() {
		return "admin/addAuthor";
	}

	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET)
	public String viewAuthors() {
		return "admin/viewAuthors";
	}

	@RequestMapping(value = "/addAuthorSubmit", method = RequestMethod.POST)
	public String addAuthorSubmit(Model model,
			@RequestParam("authorName") String authorName,
			@RequestParam(value = "bookIds", required = false) Integer[] bookIds)
			throws SQLException {
		
		Author author = new Author();
		
		authorName = adminService.encodeString(authorName);
		
		author.setAuthorName(authorName);
		
		System.out.println(authorName);
		List<Book> books = new ArrayList<Book>();
		if (bookIds != null) {
			for (Integer i : bookIds) {
				Book b = new Book();
				b.setBookId(i);
				books.add(b);
			}
		}
		Integer authorId = adminService.addAuthorWithId(author);
		if (!books.isEmpty()) {
			author.setAuthorId(authorId);
			for (Book b : books) {
				adminService.addBookAuthor(author, b);
			}
		}
		model.addAttribute("addAuthor", "Author has SuccessFully added");
		return "admin/viewAuthors";
	}

	@RequestMapping(value = "/editAuthor", method = RequestMethod.GET)
	public String editAuthor(@RequestParam("authorId") Integer authorId) {
		return "admin/editAuthor";
	}

	@RequestMapping(value = "/deleteAuthor", method = RequestMethod.GET)
	public String deleteAuthor(Model model,
			@RequestParam("authorId") Integer authorId) throws SQLException {
		Author author = new Author();
		author.setAuthorId(authorId);
		adminService.deleteAuthor(author);
		model.addAttribute("deleteAuthor", "Author has SuccessFully Deleted");
		return "admin/viewAuthors";
	}

	@RequestMapping(value = "/editAuthorSubmit", method = RequestMethod.POST)
	public String editAuthorSubmit(Model model,
			@RequestParam("authorId") Integer authorId,
			@RequestParam("authorName") String authorName,
			@RequestParam(value = "bookIds", required = false) Integer[] bookIds)
			throws SQLException {
		Author author = new Author();
		author.setAuthorName(authorName);
		author.setAuthorId(authorId);

		List<Book> books = new ArrayList<Book>();
		if (bookIds != null) {
			for (Integer i : bookIds) {
				Book b = new Book();
				b.setBookId(i);
				books.add(b);
			}
		}

		adminService.editAuthor(author);

		Author currentAuthor = adminService.getOneAuthor(authorId);
		List<Book> currBooks = currentAuthor.getBooks();

		for (Book b : currBooks) {
			adminService.deleteBookAuthor(currentAuthor, b);
		}

		if (!books.isEmpty()) {
			Author a = new Author();
			a.setAuthorId(authorId);
			for (Book b : books) {
				adminService.addBookAuthor(a, b);
			}
		}
		model.addAttribute("editAuthor", "Author has SuccessFully Updated");
		return "admin/viewAuthors";
	}

	/*
	 * -------------------Book--------------------
	 */

	@RequestMapping(value = "/addBook", method = RequestMethod.GET)
	public String addBook() {
		return "admin/addBook";
	}

	@RequestMapping(value = "/viewBooks", method = RequestMethod.GET)
	public String viewBooks() {
		return "admin/viewBooks";
	}

	@RequestMapping(value = "/addBookSubmit", method = RequestMethod.POST)
	public String addBookSubmit(
			Model model,
			@RequestParam("bookTitle") String bookTitle,
			@RequestParam(value = "authorIds", required = false) Integer[] authorIds,
			@RequestParam(value = "genreIds", required = false) Integer[] genreIds,
			@RequestParam(value = "libraryBranchIds", required = false) Integer[] libraryBranchIds,
			@RequestParam(value = "publisherId") Integer publisherId)
			throws SQLException {
		
		Book book = new Book();
		book.setTitle(bookTitle);
		book.setPubId(publisherId);

		List<Author> authors = new ArrayList<Author>();
		if (authorIds != null) {
			for (Integer i : authorIds) {
				Author a = new Author();
				a.setAuthorId(i);
				authors.add(a);
			}
		}

		List<Genre> genres = new ArrayList<Genre>();
		if (genreIds != null) {
			for (Integer i : genreIds) {
				Genre g = new Genre();
				g.setGenreId(i);
				genres.add(g);
			}
		}

		List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();
		if (libraryBranchIds != null) {
			for (Integer i : libraryBranchIds) {
				LibraryBranch lb = new LibraryBranch();
				lb.setBranchId(i);
				libraryBranches.add(lb);
			}
		}
		Integer bookId = adminService.addBookWithId(book);
		book.setBookId(bookId);

		if (!authors.isEmpty()) {
			for (Author a : authors) {
				adminService.addBookAuthor(a, book);
			}
		}

		if (!genres.isEmpty()) {
			for (Genre g : genres) {
				adminService.addBookGenre(g, book);
			}
		}

		if (!libraryBranches.isEmpty()) {
			for (LibraryBranch lb : libraryBranches) {
				adminService.createBookCopies(book, lb);
			}
		}
		model.addAttribute("addBook", "Book has SuccessFully Updated");
		return "admin/viewBooks";
	}

	@RequestMapping(value = "/editBook", method = RequestMethod.GET)
	public String editBook(@RequestParam("bookId") Integer bookId) {
		return "admin/editBook";
	}

	@RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
	public String deleteBook(Model model, @RequestParam("bookId") Integer bookId)
			throws SQLException {
		Book book = new Book();
		book.setBookId(bookId);
		adminService.deleteBook(book);
		model.addAttribute("deleteBook", "Book has SuccessFully Deleted");
		return "admin/viewBooks";
	}

	@RequestMapping(value = "/editBookSubmit", method = RequestMethod.POST)
	public String editBookSubmit(
			Model model,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("bookTitle") String bookTitle,
			@RequestParam(value = "authorIds", required = false) Integer[] authorIds,
			@RequestParam(value = "genreIds", required = false) Integer[] genreIds,
			@RequestParam(value = "libraryBranchIds", required = false) Integer[] branchIds,
			@RequestParam(value = "publisherId") Integer publisherId)
			throws SQLException {

		Book book = new Book();
		book.setBookId(bookId);
		book.setTitle(bookTitle);
		book.setPubId(publisherId);

		List<Author> authors = new ArrayList<Author>();
		if (authorIds != null) {
			for (Integer i : authorIds) {
				Author a = new Author();
				a.setAuthorId(i);
				authors.add(a);
			}
		}

		List<Genre> genres = new ArrayList<Genre>();
		if (genreIds != null) {
			for (Integer i : genreIds) {
				Genre g = new Genre();
				g.setGenreId(i);
				genres.add(g);
			}
		}

		List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();
		if (branchIds != null) {
			for (Integer i : branchIds) {
				LibraryBranch lb = new LibraryBranch();
				lb.setBranchId(i);
				libraryBranches.add(lb);
			}
		}

		adminService.editBook(book);
		//
		Book currentBook = adminService.getOneBook(bookId);
		List<Author> currAuthors = currentBook.getAuthors();
		List<Genre> currGenres = currentBook.getGenres();
		List<LibraryBranch> currBranches = currentBook.getLibraryBranches();

		Book b = new Book();
		b.setBookId(bookId);

		for (Author a : currAuthors) {
			adminService.deleteBookAuthor(a, currentBook);
		}

		for (Genre g : currGenres) {
			adminService.deleteBookGenre(g, currentBook);
		}

		for (LibraryBranch lb : currBranches) {
			adminService.deleteBookCopies(lb, currentBook);
		}

		if (!libraryBranches.isEmpty()) {
			for (LibraryBranch lb : libraryBranches) {
				adminService.createBookCopies(currentBook, lb);
			}
		}

		if (!authors.isEmpty()) {
			for (Author a : authors) {
				adminService.addBookAuthor(a, currentBook);
			}
		}

		if (!genres.isEmpty()) {
			for (Genre g : genres) {
				adminService.addBookGenre(g, b);
			}
		}
		model.addAttribute("editBook", "Book has SuccessFully Added");
		return "admin/viewBooks";
	}

	@RequestMapping(value = "/overrideBookLoans", method = RequestMethod.GET)
	public String overrideBookLoans(@RequestParam("bookId") Integer bookId,
			@RequestParam("cardNo") Integer cardNo,
			@RequestParam("branchId") Integer branchId, Model model) {
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("bookId", bookId);
		model.addAttribute("branchId", branchId);
		return "admin/overrideDueDate";
	}

	@RequestMapping(value = "/overrideDueDateSubmit", method = RequestMethod.POST)
	public String overrideDueDate(@RequestParam("dueDate") String newDueDate,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("cardNo") Integer cardNo,
			@RequestParam("branchId") Integer branchId, Model model)
			throws ParseException, SQLException {
		newDueDate = newDueDate.replace("/", "");
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");

		BookLoans bl = new BookLoans();

		Book b = new Book();
		LibraryBranch lb = new LibraryBranch();
		Borrower bor = new Borrower();

		b.setBookId(bookId);
		lb.setBranchId(branchId);
		bor.setCardNo(cardNo);

		bl.setBook(b);
		bl.setBorrower(bor);
		bl.setLibraryBranch(lb);

		Date date = ft.parse(newDueDate);

		bl.setDueDate(date);

		adminService.overrideDueDate(bl);
		return "admin/admin_override";
	}

	/*
	 * Search
	 */

	@RequestMapping(value = "/searchAuthors", method = RequestMethod.GET)
	@ResponseBody
	public String searchAuthor(Locale locale, Model model,
			@RequestParam(value = "searchString") String searchString)
			throws SQLException {
		List<Author> lst = adminService.getAllAuthorsForSearch(searchString,
				-1, 10);
		int count = lst.size();

		int pages = count / 10;
		if (count % 10 != 0)
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>"
					+ i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageAuthors", method = RequestMethod.GET)
	@ResponseBody
	public String pageAuthors(
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam("pageNo") int pageNo, Model model)
			throws ParseException, SQLException {
		List<Author> authors = adminService.getAllAuthorsForSearch(
				searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();

		sb.append("<tr><th>Author ID</th>");
		sb.append("<th>Author Name</th>");
		sb.append("<th>Book Title</th>");
		sb.append("<th>EDIT</th>");
		sb.append("<th>DELETE</th></tr>");
		for (Author a : authors) {
			sb.append("<tr><td>" + a.getAuthorId() + "</td><td>"
					+ a.getAuthorName() + "</td><td>");

			for (Book b : a.getBooks()) {
				sb.append(b.getTitle() + "&nbsp; | &nbsp;");
			}

			sb.append("</td><td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal1'"
					+ "href='editAuthor?authorId="
					+ a.getAuthorId()
					+ "'>EDIT</button></td><td>"
					+ "<button type='button' class='btn btn btn-danger' "
					+ "onclick=\"javascript:location.href='deleteAuthor?authorId="
					+ a.getAuthorId() + "'\">DELETE</button></td></tr>)");
		}
		return sb.toString();
	}

	/*
	 * Book Search
	 */

	@RequestMapping(value = "/searchBooks", method = RequestMethod.GET)
	@ResponseBody
	public String searchBooks(Locale locale, Model model,
			@RequestParam(value = "searchString") String searchString)
			throws SQLException {
		List<Book> lst = adminService
				.getAllBooksForSearch(searchString, -1, 10);
		int count = lst.size();

		int pages = count / 10;
		if (count % 10 != 0)
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>"
					+ i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageBooks", method = RequestMethod.GET)
	@ResponseBody
	public String pageBooks(
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam("pageNo") int pageNo, Model model)
			throws ParseException, SQLException {
		List<Book> books = adminService.getAllBooksForSearch(searchString,
				pageNo, 10);
		StringBuilder sb = new StringBuilder();

		sb.append("<tr><th>#</th>");
		sb.append("<th>Book Title</th>");
		sb.append("<th>Author Name</th>");
		sb.append("<th>Genre Name</th>");
		sb.append("<th>Publisher Name</th>");
		sb.append("<th>EDIT</th>");
		sb.append("<th>DELETE</th></tr>");
		for (Book b : books) {
			sb.append("<tr><td>" + b.getBookId() + "</td><td>" + b.getTitle()
					+ "</td><td>");

			for (Author a : b.getAuthors()) {
				sb.append(a.getAuthorName() + "&nbsp; | &nbsp;");
			}
			sb.append("</td>");
			sb.append("<td>");

			for (Genre g : b.getGenres()) {
				sb.append(g.getGenreName() + "&nbsp; | &nbsp;");
			}
			sb.append("</td>");
			sb.append("<td>");
			sb.append(b.getPublisher().getPublisherName());
			sb.append("</td><td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal2'"
					+ "href='editBook?bookId=" + b.getBookId()
					+ "'>EDIT</button></td><td>"
					+ "<button type='button' class='btn btn btn-danger' "
					+ "onclick=\"javascript:location.href='deleteBook?bookId="
					+ b.getBookId() + "'\">DELETE</button></td></tr>)");
		}
		return sb.toString();
	}

	/*
	 * Search Borrowers
	 */

	@RequestMapping(value = "/searchBorrowers", method = RequestMethod.GET)
	@ResponseBody
	public String searchBorrowers(Locale locale, Model model,
			@RequestParam(value = "searchString") String searchString)
			throws SQLException {
		List<Borrower> lst = adminService.getAllBorrowersForSearch(
				searchString, -1, 10);
		int count = lst.size();

		int pages = count / 10;
		if (count % 10 != 0)
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>"
					+ i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageBorrowers", method = RequestMethod.GET)
	@ResponseBody
	public String pageBorrowers(
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam("pageNo") int pageNo, Model model)
			throws ParseException, SQLException {
		List<Borrower> borrowers = adminService.getAllBorrowersForSearch(
				searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();

		sb.append("<tr><th>Card No#</th>");
		sb.append("<th>Borrower Name</th>");
		sb.append("<th>Borrower Address</th>");
		sb.append("<th>Borrower Phone</th>");
		sb.append("<th>EDIT</th>");
		sb.append("<th>DELETE</th></tr>");
		for (Borrower b : borrowers) {
			sb.append("<tr><td>"
					+ b.getCardNo()
					+ "</td><td>"
					+ b.getName()
					+ "</td>"
					+ "<td>"
					+ b.getAddress()
					+ "</td><td>"
					+ b.getPhone()
					+ "</td><td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal3'"
					+ "href='editBorrower?cardNo="
					+ b.getCardNo()
					+ "'>EDIT</button></td><td>"
					+ "<button type='button' class='btn btn btn-danger' "
					+ "onclick=\"javascript:location.href='deleteBorrower?cardNo="
					+ b.getCardNo() + "'\">DELETE</button></td></tr>)");
		}
		return sb.toString();
	}

	/*
	 * Search Publishers
	 */

	@RequestMapping(value = "/searchPublishers", method = RequestMethod.GET)
	@ResponseBody
	public String searchPublishers(Locale locale, Model model,
			@RequestParam(value = "searchString") String searchString)
			throws SQLException {
		List<Publisher> lst = adminService.getAllPublishersForSearch(
				searchString, -1, 10);
		int count = lst.size();

		int pages = count / 10;
		if (count % 10 != 0)
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>"
					+ i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pagePublishers", method = RequestMethod.GET)
	@ResponseBody
	public String pagePublishers(
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam("pageNo") int pageNo, Model model)
			throws ParseException, SQLException {
		List<Publisher> publishers = adminService.getAllPublishersForSearch(
				searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();

		sb.append("<tr><th>#</th>");
		sb.append("<th>Publisher Name</th>");
		sb.append("<th>Borrower Address</th>");
		sb.append("<th>Borrower Phone</th>");
		sb.append("<th>EDIT</th>");
		sb.append("<th>DELETE</th></tr>");
		for (Publisher p : publishers) {
			sb.append("<tr><td>"
					+ p.getPublisherId()
					+ "</td><td>"
					+ p.getPublisherName()
					+ "</td>"
					+ "<td>"
					+ p.getPublisherAddress()
					+ "</td><td>"
					+ p.getPublisherPhone()
					+ "</td><td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal4'"
					+ "href='editPublisher?publisherId="
					+ p.getPublisherId()
					+ "'>EDIT</button></td><td>"
					+ "<button type='button' class='btn btn btn-danger' "
					+ "onclick=\"javascript:location.href='deletePublisher?pubisherId="
					+ p.getPublisherId() + "'\">DELETE</button></td></tr>)");
		}

		return sb.toString();
	}

	/*
	 * Genres
	 */
	@RequestMapping(value = "/searchGenres", method = RequestMethod.GET)
	@ResponseBody
	public String searchGenres(Locale locale, Model model,
			@RequestParam(value = "searchString") String searchString)
			throws SQLException {
		List<Genre> lst = adminService.getAllGenresForSearch(searchString, -1,
				10);
		int count = lst.size();

		int pages = count / 10;
		if (count % 10 != 0)
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>"
					+ i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageGenres", method = RequestMethod.GET)
	@ResponseBody
	public String pageGenres(
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam("pageNo") int pageNo, Model model)
			throws ParseException, SQLException {
		List<Genre> genres = adminService.getAllGenresForSearch(searchString,
				pageNo, 10);
		StringBuilder sb = new StringBuilder();

		sb.append("<tr><th>#</th>");
		sb.append("<th>Genre Name</th>");
		sb.append("<th>EDIT</th>");
		sb.append("<th>DELETE</th></tr>");
		for (Genre g : genres) {
			sb.append("<tr><td>"
					+ g.getGenreId()
					+ "</td><td>"
					+ g.getGenreName()
					+ "</td>"
					+ "<td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal5'"
					+ "href='editGenre?genreId="
					+ g.getGenreId()
					+ "'>EDIT</button></td><td>"
					+ "<button type='button' class='btn btn btn-danger' "
					+ "onclick=\"javascript:location.href='deleteGenre?genreId="
					+ g.getGenreId() + "'\">DELETE</button></td></tr>)");
		}
		return sb.toString();
	}

	/*
	 * Genres
	 */
	@RequestMapping(value = "/searchLibraryBranches", method = RequestMethod.GET)
	@ResponseBody
	public String searchLibraryBranches(Locale locale, Model model,
			@RequestParam(value = "searchString") String searchString)
			throws SQLException {
		List<LibraryBranch> lst = adminService.getAllBranchesForSearch(
				searchString, -1, 10);
		int count = lst.size();

		int pages = count / 10;
		if (count % 10 != 0)
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>"
					+ i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageLibraryBranches", method = RequestMethod.GET)
	@ResponseBody
	public String pageLibraryBranches(
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam("pageNo") int pageNo, Model model)
			throws ParseException, SQLException {
		List<LibraryBranch> branches = adminService.getAllBranchesForSearch(
				searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();

		sb.append("<tr><th>#</th>");
		sb.append("<th>Library Branch Name</th>");
		sb.append("<th>Library Branch Address</th>");
		sb.append("<th>EDIT</th>");
		sb.append("<th>DELETE</th></tr>");
		for (LibraryBranch lb : branches) {
			sb.append("<tr><td>"
					+ lb.getBranchId()
					+ "</td><td>"
					+ lb.getBranchName()
					+ "</td>"
					+ "<td>"
					+ lb.getBranchAddress()
					+ "</td><td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal6'"
					+ "href='adminEditLibraryBranch?branchId="
					+ lb.getBranchId()
					+ "'>EDIT</button></td><td>"
					+ "<button type='button' class='btn btn btn-danger' "
					+ "onclick=\"javascript:location.href='deleteLibraryBranch?branchId="
					+ lb.getBranchId() + "'\">DELETE</button></td></tr>)");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/searchOverride", method = RequestMethod.GET)
	@ResponseBody
	public String searchOverride(Locale locale, Model model,
			@RequestParam(value = "searchString") String searchString)
			throws SQLException {
		List<BookLoans> lst = adminService.getAllBookLoansForSearch(
				searchString, -1, 10);
		int count = lst.size();

		int pages = count / 10;
		if (count % 10 != 0)
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>"
					+ i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageOverride", method = RequestMethod.GET)
	@ResponseBody
	public String pageOverride(
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam("pageNo") int pageNo, Model model)
			throws ParseException, SQLException {
		List<BookLoans> bookLoans = adminService.getAllBookLoansForSearch(
				searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();

		sb.append("<tr><th>Borrower Card No</th>");
		sb.append("<th>Borrower Name</th>");
		sb.append("<th>Book Id</th>");
		sb.append("<th>Book Title</th>");
		sb.append("<th>Branch Id</th>");
		sb.append("<th>Branch Name</th>");
		sb.append("<th>Branch Address</th>");
		sb.append("<th>Due Date</th>");
		sb.append("<th>Override</th></tr>");
		for (BookLoans bl : bookLoans) {
			sb.append("<tr><td>"
					+ bl.getBorrower().getCardNo()
					+ "</td>"
					+ "<td>"
					+ bl.getBorrower().getName()
					+ "</td>"
					+ "<td>"
					+ bl.getBook().getBookId()
					+ "</td>"
					+ "<td>"
					+ bl.getBook().getTitle()
					+ "</td>"
					+ "<td>"
					+ bl.getLibraryBranch().getBranchId()
					+ "</td>"
					+ "<td>"
					+ bl.getLibraryBranch().getBranchName()
					+ "</td>"
					+ "<td>"
					+ bl.getLibraryBranch().getBranchAddress()
					+ "</td>"
					+ "<td>"
					+ bl.getDueDate()
					+ "</td>"
					+ "<td><button type='button' "
					+ "class='btn btn btn-danger'data-toggle='modal' data-target='#myModalmodal'"
					+ "href='overrideBookLoans?cardNo="
					+ bl.getBorrower().getCardNo() + "&branchId="
					+ bl.getLibraryBranch().getBranchId() + "&dueDate="
					+ bl.getDueDate() + "&bookId=" + bl.getBook().getBookId()
					+ "'>Override</button></td>" + "</tr>");
		}
		return sb.toString();
	}
}