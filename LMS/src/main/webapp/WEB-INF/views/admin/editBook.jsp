<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	
	Book currentBook = service.getOneBook(bookId);
	List<Author> currentBookAuthors = currentBook.getAuthors();
	List<Genre> currentBookGenres = currentBook.getGenres();
	Publisher currentBookPublisher = currentBook.getPublisher();
	List<LibraryBranch> currentBookLibraryBranches = currentBook.getLibraryBranches();
	
	List<Author> authors = service.getAllAuthors();
	List<Genre> genres = service.getGenres();
	List<LibraryBranch> libraryBranches = service.getLibraryBranchs();
	List<Publisher> publishers = service.getPublishers();
	
%>

<div class="modal-body">
<form action="editBookSubmit" method="post">
	<h2>Enter Book's new Details</h2>
	<table class="table tbl-bordered">
		<tr>
			<td>Enter Book Title:</td>
			<td><input class="form-control" type="text" name="bookTitle"
				value='<%=currentBook.getTitle()%>' /></td>
		</tr>
		<tr>
			<td>Select the Authors:</td>
			<td><select class="form-control" multiple="multiple" name="authorIds">
					<%
						Integer index = 0;
						for (Author a : authors) {
							if (!currentBookAuthors.isEmpty()
									&& (currentBookAuthors.size() > index)
									&& a.getAuthorId() == currentBookAuthors.get(index).getAuthorId()) {
					%>
					<option value='<%out.print(a.getAuthorId());%>' selected>
					<%
							out.print(a.getAuthorName());
					%>
					</option>
					<%
							index++;
							} else {
					%>
					<option value="<%out.print(a.getAuthorId());%>">
					<%
							out.print(a.getAuthorName());
					%>
					</option>
					<%
						}
					%>
					<%
						}
					%>
			</select>
			</td>
		</tr>
		
		<tr>
			<td>Select the Genres:</td>
			<td><select class="form-control" multiple="multiple" name="genreIds">
					<%
						Integer index1 = 0;
						for (Genre g : genres) {
							if (!currentBookGenres.isEmpty()
									&& (currentBookGenres.size() > index1)
									&& g.getGenreId() == 
									currentBookGenres.get(index1).getGenreId()) {
					%>
					<option value='<%out.print(g.getGenreId());%>' selected>
					<%
							out.print(g.getGenreName());
					%>
					</option>
					<%
							index1++;
							} else {
					%>
					<option value="<%out.print(g.getGenreId());%>">
					<%
							out.print(g.getGenreName());
					%>
					</option>
					<%
						}
					%>
					<%
						}
					%>
			</select>
			</td>
		</tr>
		
		
		<tr>
		<td>Select the Publisher:</td>
		<td><select class="form-control" size=<%=publishers.size() - 1%> name="publisherId">
					<%
						for (Publisher p : publishers) {
							if (p.getPublisherId() == currentBookPublisher.getPublisherId()) {
					%>
					<option value='<%out.print(p.getPublisherId());%>' selected>
					<%
							out.print(p.getPublisherName());
					%>
					</option>
					<%
							} else {
					%>
					<option value="<%=p.getPublisherId()%>">
					<%
							out.print(p.getPublisherName());
					%>
					</option>
					<%
						}
					%>
					<%
						}
					%>
			</select>
			</td>
		</tr>
		<tr>
			<td>Select the Library Branches:</td>
			<td><select class="form-control" multiple="multiple" name="libraryBranchIds">
					<%
						Integer index2 = 0;
						for (LibraryBranch lb : libraryBranches) {
							if (!currentBookLibraryBranches.isEmpty()
									&& (currentBookLibraryBranches.size() > index2)
									&& lb.getBranchId() == currentBookLibraryBranches.get(index2).getBranchId()) {
					%>
					<option value='<%out.print(lb.getBranchId());%>' selected>
					<%
							out.print(lb.getBranchName());
					%>
					</option>
					<%
							index2++;
							} else {
					%>
					<option value="<%out.print(lb.getBranchId());%>">
					<%
							out.print(lb.getBranchName());
					%>
					</option>
					<%
						}
					%>
					<%
						}
					%>
			</select>
			</td>
		</tr>
	</table>
	<input type="hidden" name="bookId" value=<%=bookId%>>
<input class="btn btn-success" type="submit" value="Edit Book">
</form>
</div>