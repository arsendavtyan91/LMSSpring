package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCount;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

@Component
@Service
public class BorrowerService {

	@Autowired
	BorrowerDAO borrowerDAO;

	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	LibraryBranchDAO libraryBranchDAO;

	@Autowired
	BookLoansDAO bookLoansDAO;
	
	@Transactional
	public boolean checkIfValidCardNo(Integer cardNo) throws SQLException {
		boolean b = false;
		List<Borrower> borrowers = borrowerDAO.getBorrowerByCardNo(cardNo);
		if (borrowers.isEmpty()) {
			b = false;
		} else {
			b = true;
		}
		return b;
	}
	
	public String getBorrowerNameByCardNo(int cardNo) throws SQLException {
		return borrowerDAO.getOneBorrowerByCardNo(cardNo).getName();
	}
	
	public List<LibraryBranch> getAllLibraryBranches() throws SQLException {
		return libraryBranchDAO.readAll();
	}

	public List<BookCount> getAllBooksFromLibraryBranch(Integer branchId) throws SQLException {
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(branchId);
		return libraryBranchDAO.getBooksFromBranch(lb);
	}
	
	@Transactional
	public void checkOutBook(int cardNo, int bookId, int branchId)
			throws SQLException {
		Book b = new Book();
		LibraryBranch lb = new LibraryBranch();
		Borrower borrower = new Borrower();

		BookLoans bookLoans = new BookLoans();

		lb.setBranchId(branchId);
		b.setBookId(bookId);

		borrower.setCardNo(cardNo);
		bookLoans.setBorrower(borrower);
		bookLoans.setLibraryBranch(lb);
		bookLoans.setBook(b);
		bookLoans.setDateIn(Calendar.getInstance().getTime());
		bookLoans.setDateOut(Calendar.getInstance().getTime());

		Calendar cal = Calendar.getInstance();
		cal.setTime(cal.getTime());
		cal.add(Calendar.DATE, 7);

		bookLoans.setDueDate(cal.getTime());

		bookLoansDAO.addBookLoans(bookLoans);
	}
	
	@Transactional
	public void returnBook(BookLoans bookLoans) throws SQLException {
		bookLoansDAO.deleteBookLoans(bookLoans);
	}
	
	@Transactional
	public void returnBook(Integer cardNo, Integer branchId, Integer bookId) throws SQLException {
		Book b = new Book();
		LibraryBranch lb = new LibraryBranch();
		Borrower bw = new Borrower();

		b.setBookId(bookId);
		lb.setBranchId(branchId);
		bw.setCardNo(cardNo);

		BookLoans bookLoans = new BookLoans();
		bookLoans.setBook(b);
		bookLoans.setLibraryBranch(lb);
		bookLoans.setBorrower(bw);
		bookLoansDAO.deleteBookLoans(bookLoans);
	}
	
	@Transactional
	public List<BookLoans> getAllLoansForBorrower(Borrower borrower)
			throws SQLException {
		return bookLoansDAO.getAllLoansForBorrower(borrower);
	}
	
	public List<BookLoans> getAllLoansForBorrowerForPage(Borrower borrower, int pageNo, int pageSize)
			throws SQLException {
		return bookLoansDAO.getAllLoansForBorrowerForPage(borrower, pageNo, pageSize);
	}
	
	public List<BookLoans> getAllLoansForBorrowerForSearch(Borrower borrower,String searchString, int pageNo, int pageSize)
			throws SQLException {
		return bookLoansDAO.getAllLoansForBorrowerForSearch(borrower, searchString, pageNo, pageSize);
	}
	
	public List<LibraryBranch> getAllBranchesForSearch(String searchString, int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.readAllBranchesForSearch(searchString, pageNo, pageSize);
	}
	
	public List<LibraryBranch> getAllBranchesForPageNo(int pageNo, int pageSize) throws SQLException {
		return libraryBranchDAO.readAllBranchesForPage(pageNo, pageSize);
	}
	
	public List<Book> getAllBooksNonRepeatingBooksFromLibraryBranch(Integer branchId, Integer cardNo) throws SQLException {
		return bookDAO.getAllBooksNonRepeatingBooksFromLibraryBranch(branchId, cardNo);
	}
	
	public List<Book> getAllBooksNonRepeatingBooksFromLibraryBranchForPage(Integer branchId, Integer cardNo, int pageNo, int pageSize) throws SQLException {
		return bookDAO.getAllBooksNonRepeatingBooksFromLibraryBranchForPage(branchId, cardNo, pageNo, pageSize);
	}
	
	public List<Book> getAllBooksNonRepeatingBooksFromLibraryBranchForSearch(Integer branchId, Integer cardNo, String searchString, int pageNo, int pageSize) throws SQLException {
		return bookDAO.getAllBooksNonRepeatingBooksFromLibraryBranchForSearch(branchId, cardNo, searchString, pageNo, pageSize);
	}
}