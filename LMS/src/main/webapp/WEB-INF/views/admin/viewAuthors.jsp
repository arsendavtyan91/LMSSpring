<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>


<script>


setTimeout(function() {
    $('#addAuthor').fadeOut('fast');
}, 3000); 

setTimeout(function() {
    $('#editAuthor').fadeOut('fast');
}, 3000); 

setTimeout(function() {
    $('#deleteAuthor').fadeOut('fast');
}, 3000); 

</script>


<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");
	
	List<Author> allAuthors = service.getAllAuthors();
	
	Integer numPages = allAuthors.size()/10;
	if (allAuthors.size() % 10 != 0) 
		numPages++;
	List<Author> authors = service.getAllAuthorsForPageNo(1, 10);
	
%>

<script>
	function search() {
		$.ajax({
			url: "searchAuthors",
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
		url: "pageAuthors",
			data: {
			searchString: $('#searchString').val(),
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchAuthors" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Author's name to search" onkeyup="search();">
</form>

<h2 align="center">List of Authors in LMS</h2>
<h3 align = "center"><p id="addAuthor"><font color="green">${addAuthor}</font></p></h3>
<h3 align = "center"><p id="editAuthor"><font color="green">${editAuthor}</font></p></h3>
<h3 align = "center"><p id="deleteAuthor"><font color="green">${deleteAuthor}</font></p></h3>

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
<button class="btn btn-success" type="button" class="btn" onclick="javascript:location.href='addAuthor'">Add New Author</button>
	<table class="table table-striped table-bordered table-hover" id="authorsTable">
		<thead>
			<tr>
				<th>Author ID</th>
				<th>Author Name</th>
				<th>Book Title</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Author a : authors) {
			%>
			<tr>
				<td><%=a.getAuthorId()%></td>
				<td><%=a.getAuthorName()%></td>
				<td>
					<%
	 						for (Book b : a.getBooks()) {
   					%>  
  						<%=b.getTitle()%>&nbsp; | &nbsp;
 						<%  
    						}
    						%> 
				
				
				<td><button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal1"
				href="editAuthor?authorId=<%=a.getAuthorId()%>">EDIT</button></td>
				<td><button class="btn btn-danger" type="button" class="btn" onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorId()%>'">Delete</button></td>
					<%
						}
					%>
		</tbody>
	</table>
	
<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
