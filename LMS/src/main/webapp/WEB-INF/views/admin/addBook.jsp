<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%@include file="../include.html"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");

	List<Author> authors = service.getAllAuthors();
	List<Genre> genres = service.getGenres();
	List<Publisher> publishers = service.getPublishers();
	List<LibraryBranch> libraryBranches = service.getLibraryBranchs();
%>

<form action="addBookSubmit" method="post">
	<h2>Please Enter Book Details</h2>
	<table class="table">
		<tr>
			<td>Enter Book Name:</td>
			<td><input class="form-control" type="text" name="bookTitle" /></td>
		</tr>
		<tr>
			<td>Select the authors:</td>
			<td><select class="form-control" multiple="multiple" name="authorIds">
					<%
						for (Author a : authors) {
					%>
					<option value=<%=a.getAuthorId()%>>
					<%
						out.println(a.getAuthorName());
					%>
					</option>
					<%
					}
					%>
			</select></td>
		</tr>
		<tr>
			<td>Select the genres:</td>
			<td><select class="form-control" multiple="multiple" name="genreIds">
					<%
						for (Genre g : genres) {
					%>
					<option value=<%=g.getGenreId()%>>
						<%
							out.println(g.getGenreName());
						%>
					</option>
					<%
						}
					%>
			</select></td>
		</tr>
	
		<tr>
			<td>Select the Library Branch</td>
			<td><select class="form-control" multiple="multiple" name="libraryBranchIds">
					<%
						for (LibraryBranch lb : libraryBranches) {
					%>
					<option value=<%=lb.getBranchId()%>>
					<%
						out.println(lb.getBranchName());
					%>
					</option>
					<%
						}
					%>
			</select></td>
		</tr>
			<tr>
			<td>Select the Publisher</td>
			<td><select class="form-control" size=<%=publishers.size() - 1%> name="publisherId">
					<%
						for (Publisher p : publishers) {
					%>
							<option value=<%=p.getPublisherId()%>>
								<%=p.getPublisherName()%> 
							
							</option>
					<%
						}
					%>
			</select>
			</td>
		</tr>
		
	</table>
	<input class="btn btn-success" type="submit" value="Add Book">
</form>