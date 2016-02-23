<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.BookCount"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html" %>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	LibrarianService service = (LibrarianService)ac.getBean("librarianService");
	
	int bId = Integer.parseInt(request.getParameter("branchId"));
	LibraryBranch lb = new LibraryBranch();
	lb.setBranchId(bId);
	
	List<BookCount> allBookCount = service.getBooksFromBranch(lb);
	
	Integer numPages = allBookCount.size()/10;
	if (allBookCount.size() % 10 != 0) 
		numPages++;
	List<BookCount> bookCount = service.getBooksFromBranchForPage(lb, 1, 10);
	
%>

<script>
	
	function search() {
		$.ajax({
			url: "searchLibraryBranchesBooks",
			data: {
				searchString: $('#searchString').val(),
				branchId: <%=bId%>
			}
		}).done(function (data) {
			$('.pagination').html(data);
			paging(1);
		});
	}
	
	function paging(page) {
		$.ajax({
		url: "pageLibraryBranchesBooks",
			data: {
			searchString: $('#searchString').val(),
			branchId: <%=bId%>,
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchLibraryBranchesBooks" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Book title to search" onkeyup="search();">
</form>

<h2 align="center">Select the Book in Branch</h2>

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
	<table class="table table-striped table-bordered table-hover">

		<tr>
			<th>Book Id</th>
			<th>Book Title</th>
			<th>No Of Copies</th>
			<th>Update Copies</th>
		</tr>
		<%
			for (BookCount b : bookCount) {
		%>
		<tr>
			<td><%=b.getBook().getBookId()%></td>
			<td><%=b.getBook().getTitle()%></td>
			<td><%=b.getNoOfCopies()%></td>
			<td><button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal12"
				href="selectLibraryBranchBooks?bookId=<%=b.getBook().getBookId()%>&noOfCopies=<%=b.getNoOfCopies()%>&branchId=<%=request.getParameter("branchId")%>">Update Copies</button>

 	<%}%>
 
</table>

<div id="myModal12" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
