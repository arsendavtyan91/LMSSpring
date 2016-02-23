<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	LibrarianService service = (LibrarianService)ac.getBean("librarianService");
	
	Integer noOfCopies = Integer.parseInt(request.getParameter("noOfCopies"));
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	
%>

<div class="modal-body">
<label for="usr">Current amount of copies is <%=noOfCopies%> </label>
<form action="updateNoOfCopies" method="post">
Edit the Book Copies:&nbsp;&nbsp; <input class="form-control" type="text" name="noOfCopies" value=""> <br/>
<input type="hidden" name="branchId" value=<%=branchId%> >
<input type="hidden" name="bookId" value=<%=bookId%> >
<button type="submit" class="btn btn-success">Edit Number Of Copies	</button>
</form>
</div>