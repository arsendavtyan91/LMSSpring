<%@include file="../include.html"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page
	import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<Book> books = service.getAllBooks();
%>

<form action="addAuthorSubmit" method="post">
<div class="col-xs-3">
		<label for="usr">Enter Author Name:</label> 
		<input placeholder="Author Name" type="text" class="form-control" name="authorName" /> 
		<br>
		<label for="usr">Select Books</label> 
		<select class="form-control" multiple="multiple" name="bookIds">
		<%
			for (Book b : books) {
		%>
		<option value=<%=b.getBookId()%>>
			<%
				out.println(b.getTitle());
			%>
		</option>
		<%
			}
		%>
	</select>
	<br> 
	<input type="submit" class="btn btn-success" value="Add Author">
	
	</div>
</form>