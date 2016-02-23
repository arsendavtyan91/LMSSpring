<%@include file="../include.html"%>

<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	LibrarianService service = (LibrarianService)ac.getBean("librarianService");
	List<LibraryBranch> allBranches = service.getAllLibraryBranches();
	
	Integer numPages = allBranches.size()/10;
	if (allBranches.size() % 10 != 0) 
		numPages++;
	List<LibraryBranch> branches = service.getAllBranchesForPageNo(1, 10);
%>
<script>
	
	function search() {
		$.ajax({
			url: "searchLibraryBranchesLibraian",
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
		url: "pageLibraryBranchesLibrarian",
			data: {
			searchString: $('#searchString').val(),
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchLibraryBranchesLibraian" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Author's name to search" onkeyup="search();">
</form>

<h2 align="center">Enter the branch your manage</h2>

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
<table class="table table-striped table-bordered table-hover" id="authorsTable">
		<tr>
			<th>#</th>
			<th>Library Branch Name</th>
			<th>Library Branch Address</th>
			<th>Edit</th>
			<th>Select</th>
		</tr>
		<%
			for (LibraryBranch lb : branches) {
		%>
		<tr>
			<td><%=lb.getBranchId()%></td>
			<td><%=lb.getBranchName()%></td><td><%=lb.getBranchAddress()%></td>
 			<td><button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal10"
				href="edit?branchId=<%=lb.getBranchId()%>">EDIT</button></td>
 			<td><button class="btn btn-success" type="button" onclick="javascript:location.href='select?branchId=<%=lb.getBranchId()%>'">Select</button>
 		<%}%>
</table>

<div id="myModal10" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
