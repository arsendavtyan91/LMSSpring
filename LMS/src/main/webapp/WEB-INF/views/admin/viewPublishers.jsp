<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<Publisher> allPublishers = service.getPublishers();
	Integer numPages = allPublishers.size()/10;
	if (allPublishers.size() % 10 != 0) 
		numPages++;
	List<Publisher> publishers = service.getAllPublishersForPageNo(1, 10);
%>

<h2>List of Publishers in Library:</h2>
<script>
	$(document).on('hidden.bs.modal', '.modal', function () {
	$(this).removeData('bs.modal');
	});
	
	function search() {
		$.ajax({
			url: "searchPublishers",
			data: {
				searchString: $('#searchString').val(),
				publisherId: <%=request.getParameter("publisherId")%>
			}
		}).done(function (data) {
			$('.pagination').html(data);
			paging(1);
		});
	}
	
	function paging(page) {
		$.ajax({
		url: "pagePublishers",
			data: {
			searchString: $('#searchString').val(),
			publisherId: <%=request.getParameter("publisherId")%>
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchPublishers" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Publishers's name to search" onkeyup="search();">
</form>

<h2 align="center">List of Publishers in LMS</h2>

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
	<button class="btn btn-success" type="button" class="btn" onclick="javascript:location.href='addPublisher'">Add New Publisher</button>
	<table class="table table-striped table-bordered table-hover" id="publishersTable">
		<tr>
			<th>#</th>
			<th>Publisher Name</th>
			<th>Publisher Address</th>
			<th>Publisher Phone</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%
			for (Publisher p : publishers) {
		%>
		<tr>
			<td><%=p.getPublisherId()%></td>
			<td><%=p.getPublisherName()%></td>
			<td><%=p.getPublisherAddress()%></td>
			<td><%=p.getPublisherPhone()%></td>
<td>
				<button type="button" class="btn btn-info"
					data-toggle="modal" data-target="#myModal4"
					href="editPublisher?publisherId=<%=p.getPublisherId()%>">EDIT
				</button>
					</td>
 			<td><button class="btn btn-danger" type="button" onclick="javascript:location.href='deletePublisher?publisherId=<%=p.getPublisherId()%>'">Delete</button>
 		</tr>
 		<%}%>
</table>
<div id="myModal4" class="modal fade" tabindex="-1" role="dialog"
aria-labelledby="myLargeModalLabel">
<div class="modal-dialog modal-lg">
	<div class="modal-content"></div>
</div>
</div>
