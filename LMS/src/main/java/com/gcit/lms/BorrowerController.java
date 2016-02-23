package com.gcit.lms;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.BorrowerService;

@Controller
public class BorrowerController {

	@Autowired
	BorrowerService borrowerService;
	
	/*
	 * ----------------------------Start of Borrower-------------------------
	 */
	
	@RequestMapping(value = "/options", method = RequestMethod.POST)
	public String borrowerOptions(@RequestParam("cardNo")int cardNo, Model model) throws SQLException {
		
		boolean b = borrowerService.checkIfValidCardNo(cardNo);
		if (b) {
			String bName = borrowerService.getBorrowerNameByCardNo(cardNo);
			model.addAttribute("bName", bName);
			return "borrower/options";
		} else {
			String res = "Invalid Card Number.Please Try again!";
			model.addAttribute("message", res);
			model.addAttribute("isValid", false);
			model.addAttribute("cardNo", cardNo);
			return "main/borrower";
		}
		
	}
	
	@RequestMapping(value = "checkOutBookBranches", method = RequestMethod.POST)
	public String borrowerCheckOutBookBranches(@RequestParam("cardNo")int cardNo, Model model) {
		model.addAttribute("cardNo", cardNo);
		return "borrower/checkOutBookBranches";
	}
	
	@RequestMapping(value = "checkOutBookBooks", method = RequestMethod.GET)
	public String borrowerCheckOutBookBooks(@RequestParam("cardNo")int cardNo, @RequestParam("cardNo")int branchId, Model model) {
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("branchId", branchId);
		model.addAttribute("messageBorrowerCheckOut", "You succesfully Checked Out the book, please return it in 7 days!");
		return "borrower/checkOutBookBooks";
	}
	
	@RequestMapping(value = "selectBook", method = RequestMethod.GET)
	public String borrowerCheckOutBookSelectBook(@RequestParam("cardNo")int cardNo,@RequestParam("bookId")int bookId, @RequestParam("branchId")int branchId, Model model) throws SQLException {
		borrowerService.checkOutBook(cardNo, bookId, branchId);
		model.addAttribute("messageBorrowerCheckOut", "You succesfully Got the book, please return it in 7 days!");
		return "main/borrower";
	}

	@RequestMapping(value = "returnBook", method = RequestMethod.POST)
	public String borrowerReturnBook() {
		return "borrower/returnBook";
	}
	
	@RequestMapping(value = "showBooks", method = RequestMethod.GET)
	public String borrowerReturnBookShowBooks(@RequestParam("cardNo")int cardNo,@RequestParam("bookId")int bookId, @RequestParam("branchId")int branchId, Model model) throws SQLException {
		borrowerService.returnBook(cardNo, branchId, bookId);
		model.addAttribute("borrowerReturnBook", "You have successfully returned the Book");
		return "main/borrower";
	}
	
	/*
	 * ----------------------------End of Borrower-------------------------
	 */
	
	@RequestMapping(value = "/searchLibraryBranchesBorrower", method = RequestMethod.GET)
	@ResponseBody
	public String searchLibraryBranches(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) throws SQLException {
		List<LibraryBranch> lst = borrowerService.getAllBranchesForSearch(searchString, -1, 10);
		int count = lst.size();
		
		int pages = count / 10;
		if (count % 10 != 0) 
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageLibraryBranchesBorrower", method = RequestMethod.GET)
	@ResponseBody
	public String pageLibraryBranches(@RequestParam (value = "searchString", required = false)String searchString, @RequestParam("pageNo")int pageNo,@RequestParam("cardNo")int cardNo, Model model) throws ParseException, SQLException {
		List<LibraryBranch> branches = borrowerService.getAllBranchesForSearch(searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();
		System.out.println("Mtnuma Vapshe?2 " + cardNo + " p " + pageNo + " Size: " + branches.size());
		sb.append("<tr><th>Branch ID</th>");
		sb.append("<th>Library Branch Name</th>");
		sb.append("<th>Library Branch Address</th>");
		sb.append("<th>Select</th></tr>");
		for (LibraryBranch lb : branches) {
			sb.append("<tr><td>" + lb.getBranchId() + "</td>" + "<td>" + lb.getBranchName() + "</td>"
					+ "<td>" + lb.getBranchAddress() + "</td>"
					+ "<td><button type='button' class='btn btn btn-info'"
					+ "onclick=\"javascript:location.href='checkOutBookBooks?cardNo=" + cardNo + "&" + "branchId=" + lb.getBranchId() + "'\">Select</button></td></tr>");
		}
		
		return sb.toString();
	}
	
	@RequestMapping(value = "/searchLibraryBranchesBooksBorrower", method = RequestMethod.GET)
	@ResponseBody
	public String searchLibraryBranchesBooks(Locale locale, Model model, @RequestParam(value = "searchString") String searchString, @RequestParam(value = "branchId") int branchId, @RequestParam(value = "cardNo") int cardNo) throws SQLException {
		List<Book> lst = borrowerService.getAllBooksNonRepeatingBooksFromLibraryBranchForSearch(branchId, cardNo, searchString, -1, 10);
		int count = lst.size();
		
		int pages = count / 10;
		if (count % 10 != 0) 
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageLibraryBranchesBooksBorrower", method = RequestMethod.GET)
	@ResponseBody
	public String pageLibraryBranchesBooks(@RequestParam (value = "searchString", required = false)String searchString, @RequestParam("pageNo")int pageNo,@RequestParam(value = "branchId") int branchId, @RequestParam(value = "cardNo") int cardNo, Model model) throws ParseException, SQLException {
		List<Book> books = borrowerService.getAllBooksNonRepeatingBooksFromLibraryBranchForSearch(branchId, cardNo, searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();
		sb.append("<tr><th>Book Id in Library</th>");
		sb.append("<th>Book Title in Library</th>");
		sb.append("<th>Check Out</th></tr>");
		for (Book b : books) {
			sb.append("<tr><td>" + b.getBookId() + "</td>"
					+ "<td>" + b.getTitle() + "</td>"
					+ "<td><button type='button' class='btn btn btn-info'"
					+ "onclick=\"javascript:location.href='selectBook?cardNo=" + cardNo + "&" + "bookId=" + b.getBookId() + "&" + "branchId=" + branchId + "'\">Check Out</button></td></tr>");
		}
		
		return sb.toString();
	}	

	
	@RequestMapping(value = "/searchBookLoans", method = RequestMethod.GET)
	@ResponseBody
	public String searchBookLoans(Locale locale, Model model, @RequestParam(value = "searchString") String searchString, @RequestParam(value = "cardNo") int cardNo) throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		List<BookLoans> lst = borrowerService.getAllLoansForBorrowerForSearch(borrower, searchString, -1, 10);
		int count = lst.size();
		
		int pages = count / 10;
		if (count % 10 != 0) 
			pages++;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= pages; i++) {
			sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
		}
		return sb.toString();
	}

	@RequestMapping(value = "/pageBookLoans", method = RequestMethod.GET)
	@ResponseBody
	public String pageBookLoans(@RequestParam (value = "searchString", required = false)String searchString, @RequestParam("pageNo")int pageNo,@RequestParam(value = "cardNo") int cardNo, Model model) throws ParseException, SQLException {
		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		List<BookLoans> booksLoans = borrowerService.getAllLoansForBorrowerForSearch(borrower, searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();
		
		sb.append("<tr><th>Book Title</th>");
		sb.append("<th>Branch Name</th>");
		sb.append("<th>Branch Address</th>");
		sb.append("<th>Due Date</th>");
		sb.append("<th>Return</th></tr>");
		for (BookLoans bl : booksLoans) {
			sb.append("<tr><td>" + bl.getBook().getTitle() + "</td>"
					+ "<td>" + bl.getLibraryBranch().getBranchName() + "</td>"
					+ "<td>" + bl.getLibraryBranch().getBranchAddress() + "</td>"
					+ "<td>" + bl.getDueDate() + "</td>"
					+ "<td><button type='button' class='btn btn btn-danger'"
					+ "onclick=\"javascript:location.href='showBooks?cardNo=" + cardNo + "&" + "bookId=" + bl.getBook().getBookId() + "&" + "branchId=" + bl.getLibraryBranch().getBranchId() + "'\">Return</button></td></tr>");
		}
		
		return sb.toString();
	}	

}
