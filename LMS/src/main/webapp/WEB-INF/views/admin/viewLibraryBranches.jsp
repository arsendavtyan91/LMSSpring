<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<LibraryBranch> allBranches = service.getLibraryBranchs();
	Integer numPages = allBranches.size()/10;
	if (allBranches.size() % 10 != 0) 
		numPages++;
	List<LibraryBranch> branches = service.getAllBranchesForPageNo(1, 10);
	
%>

<script>
	$(document).on('hidden.bs.modal', '.modal', function () {
	$(this).removeData('bs.modal');
	});
	
	function search() {
		$.ajax({
			url: "searchLibraryBranches",
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
		url: "pageLibraryBranches",
			data: {
			searchString: $('#searchString').val(),
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchLibraryBranches" method="post">
	<input type="text" class="form-control col-md-3" id="searchString"
		name="searchString" placeholder="Enter Branch name to search" onkeyup="search();">
</form>

<h2 align="center">List of Library Branches in LMS:</h2>

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
<button class="btn btn-success" type="button" class="btn" onclick="javascript:location.href='addLibraryBranch'">Add New Branch</button>
	<table class="table table-striped table-bordered table-hover" id="branchesTable">
		<tr>
			<th>#</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%
			for (LibraryBranch lb : branches) {
		%>
		<tr>
			<td><%=lb.getBranchId()%></td>
			<td><%=lb.getBranchName()%></td>
			<td><%=lb.getBranchAddress()%></td>
 			<td>
				<button type="button" class="btn btn-info"
					data-toggle="modal" data-target="#myModal6"
					href="adminEditLibraryBranch?branchId=<%=lb.getBranchId()%>">EDIT
				</button>
					</td>
 			<td><button class="btn btn-danger" type="button" onclick="javascript:location.href='deleteLibraryBranch?branchId=<%=lb.getBranchId()%>'">Delete</button>
 		</tr>
 		<%}%>
</table>
<div id="myModal6" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
