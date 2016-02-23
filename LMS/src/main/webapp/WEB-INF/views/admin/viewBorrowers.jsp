<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<Borrower> allBorrowers = service.getBorrowers();
	
	Integer numPages = allBorrowers.size()/10;
	if (allBorrowers.size() % 10 != 0) 
		numPages++;
	List<Borrower> borrowers = service.getAllBorrowersForPageNo(1, 10);
%>

<script>
	$(document).on('hidden.bs.modal', '.modal', function () {
	$(this).removeData('bs.modal');
	});
	
	function search() {
		$.ajax({
			url: "searchBorrowers",
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
		url: "pageBorrowers",
			data: {
			searchString: $('#searchString').val(),
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchBorrowers" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Borrowers's name to search" onkeyup="search();">
</form>
<h2 align="center">List of Borrowers in Library</h2>
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
<button class="btn btn-success" type="button" class="btn" onclick="javascript:location.href='addBorrower'">Add New Borrower</button>
	<table class="table table-striped table-bordered table-hover" id="borrowersTable">
		<tr>
			<th>Card No#</th>
			<th>Borrower Name</th>
			<th>Borrower Address</th>
			<th>Borrower Phone</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%
			for (Borrower b : borrowers) {
		%>
		<tr>
			<td><%=b.getCardNo()%></td>
			<td><%=b.getName()%></td>
			<td><%=b.getAddress()%></td>
			<td><%=b.getPhone()%></td>
			
			<td><button type="button" class="btn btn-info"
					data-toggle="modal" data-target="#myModal3"
					href="editBorrower?cardNo=<%=b.getCardNo()%>">EDIT
				</button></td>
 			<td><button class="btn btn-danger" type="button" onclick="javascript:location.href='deleteBorrower?cardNo=<%=b.getCardNo()%>'">Delete</button>
 		</tr>
 		<%}%>
</table>

<div id="myModal3" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>