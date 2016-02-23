<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>


<script>


setTimeout(function() {
    $('#addBook').fadeOut('fast');
}, 3000); 

setTimeout(function() {
    $('#editBook').fadeOut('fast');
}, 3000); 

setTimeout(function() {
    $('#deleteBook').fadeOut('fast');
}, 3000); 

</script>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<Book> allBooks = service.getAllBooks();
	
	Integer numPages = allBooks.size()/10;
	if (allBooks.size() % 10 != 0) 
		numPages++;
	List<Book> books = service.getAllBooksForPageNo(1, 10);
%>

<script>
	$(document).on('hidden.bs.modal', '.modal', function () {
	$(this).removeData('bs.modal');
	});
	
	function search() {
		$.ajax({
			url: "searchBooks",
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
		url: "pageBooks",
			data: {
			searchString: $('#searchString').val(),
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>
<form action="searchBooks" method="post">
	<input type="text" class="col-md-3 form-control" id="searchString"
		name="searchString" placeholder="Enter Book's title to search" onkeyup="search();">
</form>

<h2 align="center">List of Books in LMS:</h2>
<h3 align = "center"><p id="addBook"><font color="green">${addBook}</font></p></h3>
<h3 align = "center"><p id="editBook"><font color="green">${editBook}</font></p></h3>
<h3 align = "center"><p id="deleteBook"><font color="green">${deleteBook}</font></p></h3>

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
<button class="btn btn-success" type="button" class="btn" onclick="javascript:location.href='addBook'">Add New Book</button>
	<table class="table table-striped table-bordered table-hover" id="booksTable">
		<thead>
			<tr>
				<th>#</th>
				
				<th>Book Title</th>
				<th>Author Name</th>				
				<th>Genre Name</th>
				<th>Publisher Name</th>
				
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Book b : books) {
			%>
			<tr>
				<td><%=b.getBookId()%></td>
				<td><%=b.getTitle()%></td>
				<td>
					<%
						for (Author a : b.getAuthors()) {
					%> 
						<%=a.getAuthorName()%>&nbsp; | &nbsp; 
					<%
 						}
 					%>
				</td>
				
				<td>
					<%
						for (Genre g : b.getGenres()) {
					%> 
						<%=g.getGenreName()%>&nbsp; | &nbsp; 
					<%
 						}
 					%>
				</td>
				
				<td>
					<%=b.getPublisher().getPublisherName()%>&nbsp; 
				</td>
				<td>
				<button type="button" class="btn btn-info"
					data-toggle="modal" data-target="#myModal2"
					href="editBook?bookId=<%=b.getBookId()%>">EDIT
				</button>
					</td>
				<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>'">Delete</button>
					<%
						}
					%>
		</tbody>
	</table>
	
<div id="myModal2" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
