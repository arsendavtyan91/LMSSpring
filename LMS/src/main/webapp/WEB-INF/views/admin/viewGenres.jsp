<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<Genre> allGenres = service.getGenres();
	
	Integer numPages = allGenres.size()/10;
	if (allGenres.size() % 10 != 0) 
		numPages++;
	List<Genre> genres = service.getAllGenresForPageNo(1, 10);
%>
<script>
	$(document).on('hidden.bs.modal', '.modal', function () {
	$(this).removeData('bs.modal');
	});
	
	function search() {
		$.ajax({
			url: "searchGenres",
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
		url: "pageGenres",
			data: {
			searchString: $('#searchString').val(),
			pageNo: page
			}
		}).done(function (data) {
		$('.table').html(data);
	});
}
</script>

<form action="searchGenres" method="post">
	<input type="text" class="col-md-3" id="searchString"
		name="searchString" placeholder="Enter Genre's name to search" onkeyup="search();">
</form>
<h2 align="center">List of Genres in LMS:</h2>
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

	<button class="btn btn-success" type="button" class="btn" onclick="javascript:location.href='addGenre'">Add New Genre</button>
	<table class="table table-striped table-bordered table-hover" id="genreTable">
		<tr>
			<th>#</th>
			<th>Genre Name</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%
			for (Genre g : genres) {
		%>
		<tr>
			<td><%=g.getGenreId()%></td>
			<td><%=g.getGenreName()%></td>
 			<td><button type="button" class="btn btn-info"
					data-toggle="modal" data-target="#myModal5"
					href="editGenre?genreId=<%=g.getGenreId()%>">EDIT
				</button></td>
 			<td><button class="btn btn-danger" type="button" onclick="javascript:location.href='deleteGenre?genreId=<%=g.getGenreId()%>'">Delete</button>
 		</tr>
 		<%}%>
</table>

<div id="myModal5" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>