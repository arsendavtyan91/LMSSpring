<%@include file="../include.html" %>

<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	BorrowerService service = (BorrowerService)ac.getBean("borrowerService");
	
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
	List<Book> allBooks = service.getAllBooksNonRepeatingBooksFromLibraryBranch(branchId, cardNo);
	
	Integer numPages = allBooks.size()/10;
	if (allBooks.size() % 10 != 0) 
		numPages++;
	List<Book> books = service.getAllBooksNonRepeatingBooksFromLibraryBranchForPage(branchId, cardNo, 1, 10);
%>

<script>
	$(document).on('hidden.bs.modal', '.modal', function () {
	$(this).removeData('bs.modal');
	});
	
	function search() {
		$.ajax({
			url: "searchLibraryBranchesBooksBorrower",
			data: {
				searchString: $('#searchString').val(),
				cardNo : <%=request.getParameter("cardNo")%>,
				branchId : <%=request.getParameter("branchId")%>,
			}
		}).done(function (data) {
			$('.pagination').html(data);
			paging(1);
		});
	}
	
	function paging(page) {
		$.ajax({
		url: "pageLibraryBranchesBooksBorrower",
			data: {
			searchString: $('#searchString').val(),
			cardNo : <%=request.getParameter("cardNo")%>,
			branchId : <%=request.getParameter("branchId")%>,
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>
<form action="searchLibraryBranchesBooksBorrower" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Branch name to search" onkeyup="search();">
</form>

<h2 align="center">Choose The Books in Branch</h2>
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

<table class="table table-striped table-bordered table-hover" id="booksTableBorrower">

		<tr>
			<th>Book Id in Library</th>
			<th>Book Title in Library</th>
			<th>Check Out</th>
		</tr>
		<%
			for (Book b : books) {
		%>
		<tr>
			<td><%=b.getBookId()%></td><td><%=b.getTitle()%></td>
 			<td><button type="button" class='btn btn btn-info' onclick="javascript:location.href='selectBook?cardNo=<%=request.getParameter("cardNo")%>&branchId=<%=request.getParameter("branchId")%>&bookId=<%=b.getBookId()%>'">Check Out</button>
 		<%}%>
 
</table>