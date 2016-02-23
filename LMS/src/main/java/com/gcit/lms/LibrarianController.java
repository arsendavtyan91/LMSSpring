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

import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookCount;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.LibrarianService;

@Controller
public class LibrarianController {

	@Autowired
	LibrarianService librarianService;
	
	
	/*
	 * ----------------------------Start of Librarian-------------------------
	 */
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String librarianEditLibraryBranchGET(@RequestParam("branchId")int branchId, Model model) throws SQLException {
//		model.addAttribute("branchId", branchId);
		return "librarian/editLibraryBranch";
	}
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String librarianSelectLibraryBranchGET(@RequestParam("branchId")int branchId, Model model) throws SQLException {
		model.addAttribute("branchId", branchId);
		return "librarian/selectLibraryBranch";
	}
	
	@RequestMapping(value = "/editLibraryBranch", method = RequestMethod.POST)
	public String librarianEditLibraryBranchPOST(@RequestParam("branchId")int branchId, @RequestParam("branchName")String branchName, @RequestParam("branchAddress")String branchAddress) throws SQLException {
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(branchId);
		lb.setBranchName(branchName);
		lb.setBranchAddress(branchAddress);
		librarianService.editLibraryBranch(lb);
		return "main/librarian";
	}
	
	
	@RequestMapping(value = "selectLibraryBranch", method = RequestMethod.POST)
	public String librarianSelectLibraryBranchPOST(@RequestParam("branchId")int branchId, Model model) throws SQLException {
		model.addAttribute("branchId", branchId);
		return "librarian/booksList";
	}
	
	@RequestMapping(value = "selectLibraryBranchBooks", method = RequestMethod.GET)
	public String librarianSelectLibraryBranchBooks(@RequestParam("branchId")int branchId, @RequestParam("noOfCopies")int noOfCopies, @RequestParam("bookId")int bookId, Model model) throws SQLException {
		model.addAttribute("branchId", branchId);
		model.addAttribute("bookId", bookId);
		model.addAttribute("noOfCopies", noOfCopies);
		return "librarian/editNoOfCopies";
	}
	@RequestMapping(value = "editNoOfCopies", method = RequestMethod.GET)
	public String librarianEditNoOfCopies(@RequestParam("branchId")int branchId, @RequestParam("noOfCopies")int noOfCopies, @RequestParam("bookId")int bookId) throws SQLException {
		return "librarian/editNoOfCopies";
	}
	
	
	@RequestMapping(value = "updateNoOfCopies", method = RequestMethod.POST)
	public String librarianUpdateNoOfCopies(@RequestParam("branchId")int branchId, @RequestParam("noOfCopies")int noOfCopies, @RequestParam("bookId")int bookId) throws SQLException {
		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		bc.setNoOfCopies(noOfCopies);
		librarianService.updateBookCopies(bc);
		return "main/librarian";
	}
	/*
	 * ----------------------------End of Librarian-------------------------
	 */
	
	@RequestMapping(value = "/searchLibraryBranchesLibraian", method = RequestMethod.GET)
	@ResponseBody
	public String searchLibraryBranchesLibrarian(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) throws SQLException {
		List<LibraryBranch> lst = librarianService.getAllBranchesForSearch(searchString, -1, 10);
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

	@RequestMapping(value = "/pageLibraryBranchesLibrarian", method = RequestMethod.GET)
	@ResponseBody
	public String pageLibraryBranches(@RequestParam (value = "searchString", required = false)String searchString, @RequestParam("pageNo")int pageNo, Model model) throws ParseException, SQLException {
		List<LibraryBranch> branches = librarianService.getAllBranchesForSearch(searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();
		
		sb.append("<tr><th>#</th>");
		sb.append("<th>Library Branch Name</th>");
		sb.append("<th>Library Branch Address</th>");
		sb.append("<th>EDIT</th>");
		sb.append("<th>Select</th></tr>");
		for (LibraryBranch lb : branches) {
			sb.append("<tr><td>" + lb.getBranchId() + "</td><td>" + lb.getBranchName() + "</td>"
					+ "<td>" + lb.getBranchAddress() + "</td><td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal10'"
					+ "href='edit?branchId=" + lb.getBranchId() + "'>EDIT</button></td><td>"
					+ "<button type='button' class='btn btn btn-success' "
					+ "onclick=\"javascript:location.href='select?branchId=" + lb.getBranchId() + "'\">Select</button></td></tr>)");
		}
		return sb.toString();

	}
	
	@RequestMapping(value = "/searchLibraryBranchesBooks", method = RequestMethod.GET)
	@ResponseBody
	public String searchLibraryBranchesBooks(Locale locale, Model model, @RequestParam(value = "searchString", required=false) String searchString, @RequestParam("branchId") int branchId) throws SQLException {
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(branchId);
		List<BookCount> lst = librarianService.getBooksFromBranchForSearch(lb, searchString, -1, 10);
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

	@RequestMapping(value = "/pageLibraryBranchesBooks", method = RequestMethod.GET)
	@ResponseBody
	public String pageLibraryBranchesBooks(@RequestParam (value = "searchString", required = false)String searchString, @RequestParam("branchId") int branchId, @RequestParam("pageNo")int pageNo, Model model) throws ParseException, SQLException {
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(branchId);
		
		List<BookCount> bookCounts = librarianService.getBooksFromBranchForSearch(lb, searchString, pageNo, 10);
		StringBuilder sb = new StringBuilder();
		
		sb.append("<tr><th>Book Id</th>");
		sb.append("<th>Book title</th>");
		sb.append("<th>No Of Copies</th>");
		sb.append("<th>Update Copies</th></tr>");
		for (BookCount bc : bookCounts) {
			sb.append("<tr><td>" + bc.getBook().getBookId() + "</td>"
					+ "<td>" + bc.getBook().getTitle() + "</td>"
					+ "<td>" + bc.getNoOfCopies()+ "</td>"
					+ "<td><button type='button' "
					+ "class='btn btn btn-info'data-toggle='modal' data-target='#myModal12'"
					+ "href='selectLibraryBranchBooks?branchId=" + branchId + "&bookId=" + bc.getBook().getBookId() + "&noOfCopies=" + bc.getNoOfCopies() + "'>Update Copies</button></td>"
					+ "</tr>");
		}
		
		return sb.toString();

	}
}
