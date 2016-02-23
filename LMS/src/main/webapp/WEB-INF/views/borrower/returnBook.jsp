<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html" %>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	BorrowerService service = (BorrowerService)ac.getBean("borrowerService");
	Borrower borrower = new Borrower();
	
	borrower.setCardNo(Integer.parseInt(request.getParameter("cardNo")));
	List<BookLoans> allBookLoans = service.getAllLoansForBorrower(borrower);
	
	Integer numPages = allBookLoans.size()/10;
	if (allBookLoans.size() % 10 != 0) 
		numPages++;
	
	List<BookLoans> bookLoans = service.getAllLoansForBorrowerForPage(borrower, 1, 10);
%>
<script>

	
	function search() {
		$.ajax({
			url: "searchBookLoans",
			data: {
				searchString: $('#searchString').val(),
				cardNo: <%=request.getParameter("cardNo")%>
			}
		}).done(function (data) {
			$('.pagination').html(data);
			paging(1);
		});
	}
	
	function paging(page) {
		$.ajax({
		url: "pageBookLoans",
			data: {
			searchString: $('#searchString').val(),
			cardNo: <%=request.getParameter("cardNo")%>,
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchBookLoans" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Book title to search" onkeyup="search();">
</form>

<h2 align="center">List of Your Loans in LMS</h2>

<nav>
	<ul class="pagination">

		<%
			for (int i = 1; i <= numPages; i++) {
		%>
		<li><a id="page" href="javascript:paging(<%=i %>);" ><%=i%> </a></li>
		<%
		}
		%>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
	<table class="table table-striped table-bordered table-hover" id="bookLoansTable">		
	<tr>
			<th>Book Title</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>Due Date</th>
			<th>Return</th>
		</tr>
		<%
			for (BookLoans bl : bookLoans) {
		%>
		<tr>
			<td><%=bl.getBook().getTitle()%></td>
			<td><%=bl.getLibraryBranch().getBranchName()%></td>
			<td><%=bl.getLibraryBranch().getBranchAddress()%></td>
			<td><%=bl.getDueDate()%></td>
 			<td><button class='btn btn btn-danger' type="button" onclick="javascript:location.href='showBooks?cardNo=<%=request.getParameter("cardNo")%>&branchId=<%=bl.getLibraryBranch().getBranchId()%>&bookId=<%=bl.getBook().getBookId()%>'">Return</button></td>
 		</tr>
 		<%}%>
 		
</table>