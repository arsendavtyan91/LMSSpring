<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");
	Integer genreId = Integer.parseInt(request.getParameter("genreId"));
	Genre genre = service.getOneGenre(genreId);
%>

<div class="modal-body">
	<form action="editGenreSubmit" method="post">

		<table class="table tbl-bordered tbl-striped">

			<tr>
				<th>Enter Genre Name: <input class="form-control"type="text" name="genreName"
					value='<%=genre.getGenreName()%>'></th>
			</tr>
		</table>

		<input type="hidden" name="genreId" value=<%=genre.getGenreId()%>>
		<input type="submit" class="btn btn-success" value="Edit Genre"/>
	</form>
</div>