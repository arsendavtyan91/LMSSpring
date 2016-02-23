<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	LibrarianService service = (LibrarianService)ac.getBean("librarianService");
	
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	LibraryBranch lb = service.getBranchFromId(branchId);
	
%>
<div class="modal-body">
<form action="editLibraryBranch" method="post">
<label for="usr" >Enter Updated Library Branch Name: </label><input class="form-control" type="text" name="branchName" value='<%=lb.getBranchName()%>'><br/>
<label for="usr" >Enter Updated Library Branch Address: </label><input class="form-control" type="text" name="branchAddress" value='<%=lb.getBranchAddress()%>'><br/>
<input type="hidden" name="branchId" value=<%=branchId%>>
<button class="btn btn-success" type="submit">Edit Library Branch</button>
</form>
</div>