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
	
	List<LibraryBranch> allBranches = service.getAllLibraryBranches();
	
	int cardNo = Integer.parseInt(request.getParameter("cardNo"));
	
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
			url: "searchLibraryBranchesBorrower",
			data: {
				searchString: $('#searchString').val(),
				cardNo : <%=request.getParameter("cardNo")%>,
			}
		}).done(function (data) {
			$('.pagination').html(data);
			paging(1);
		});
	}
	
	function paging(page) {
		$.ajax({
		url: "pageLibraryBranchesBorrower",
			data: {
			searchString: $('#searchString').val(),
			cardNo : <%=request.getParameter("cardNo")%>,
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>
<form action="searchLibraryBranchesBorrower" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Branch name to search" onkeyup="search();">
</form>

<h2 align="center">Choose The Branch</h2>
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

<table class="table table-striped table-bordered table-hover" id="branchTableBorrower">
		<tr>
			<th>Branch ID</th>
			<th>Library Branch Name</th>
			<th>Library Branch Address</th>
			<th>Select</th>
		</tr>
		<%
			for (LibraryBranch lb : branches) {
		%>
		<tr>
			<td><%=lb.getBranchId()%></td>
			<td><%=lb.getBranchName()%></td>
			<td><%=lb.getBranchAddress()%></td>
 			<td><button type="button" class='btn btn btn-info' onclick="javascript:location.href='checkOutBookBooks?cardNo=<%=request.getParameter("cardNo")%>&branchId=<%=lb.getBranchId()%>'">Select</button>
 		<%}%>
 
</table>

</body>
</html>