<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	Integer authorId = Integer.parseInt(request.getParameter("authorId"));
	
	Author currentAuthor = service.getOneAuthor(authorId);
	List<Book> currentAuthorBooks = currentAuthor.getBooks();
	
	List<Book> books = service.getAllBooks();
%>
<div class="modal-body">
<form action="editAuthorSubmit" method="post">
	<label for="usr">Enter Author's Name:</label>
			<input class="form-control" type="text" name="authorName" value='<%=currentAuthor.getAuthorName()%>' />
			<label for="usr">Select the books:</label>
			<select class="form-control"  multiple="multiple" name="bookIds">
					<%
						Integer index = 0;
						for (Book b : books) {
							if (!currentAuthorBooks.isEmpty()
									&& (currentAuthorBooks.size() > index)
									&& b.getBookId() == currentAuthorBooks.get(index).getBookId()) {
					%>
					<option value="<%out.print(b.getBookId());%>" selected>
					<%
							out.print(b.getTitle());
					%>
					</option>
					<%
							index++;
							} else {
					%>
					<option value="<%out.print(b.getBookId());%>">
					<%
							out.print(b.getTitle());
					%>
					</option>
					<%
						}
					%>
					<%
						}
					%>
			</select>
	<input type="hidden" name="authorId" value=<%=authorId %>>
	<input class="btn btn-success" type="submit" value="Edit Author">
</form>
</div>