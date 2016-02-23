<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<BookLoans> allBookLoans = service.getAllBookLoans();
	
	Integer numPages = allBookLoans.size()/10;
	if (allBookLoans.size() % 10 != 0) 
		numPages++;
	List<BookLoans> bookLoans = service.getAllBookLoansForPage(1, 10);
%>
<script>
	function search() {
		$.ajax({
			url: "searchOverride",
			data: {
				searchString: $('#searchString').val()
			}
		}).done(function (data) {
			$('.pagination').html(data);
			paging(1);
		});
	}
	
	function paging(page) {
		$.ajax({
		url: "pageOverride",
			data: {
			searchString: $('#searchString').val(),
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchOverride" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Author's name to search" onkeyup="search();">
</form>

<h2>Choose The Book To Override the Due Date</h2>

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
			<th>Borrower Card No</th>
			<th>Borrower Name</th>
			<th>Book Id</th>
			<th>Book Title</th>
			<th>Branch Id</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>Due Date</th>
			<th>Override</th>
		</tr>
		<%
			for (BookLoans bl : bookLoans) {
		%>
		<tr>
			<td><%=bl.getBorrower().getCardNo()%></td>
			<td><%=bl.getBorrower().getName()%></td>
			<td><%=bl.getBook().getBookId()%></td>
			<td><%=bl.getBook().getTitle()%></td>
			<td><%=bl.getLibraryBranch().getBranchId()%></td>
			<td><%=bl.getLibraryBranch().getBranchName()%></td>
			<td><%=bl.getLibraryBranch().getBranchAddress()%></td>
			<td><%=bl.getDueDate()%></td>
			<td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModalmodal"
				href="overrideBookLoans?cardNo=<%=bl.getBorrower().getCardNo()%>&branchId=<%=bl.getLibraryBranch().getBranchId()%>&bookId=<%=bl.getBook().getBookId()%>&dueDate=<%=bl.getDueDate()%>">Override</button></td>
 		</tr>
 		<%}%>
</table>

<div id="myModalmodal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
