<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");
	
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));	
	LibraryBranch libraryBranch = service.getOneLibraryBranch(branchId);
%>

<div class="modal-body">
	<form action="adminEditLibraryBranchSubmit" method="post">

		<table class="table">

			<tr>
				<th>Enter Library Branch Name: <input class="form-control" placeholder="Name" type="text" name="branchName"
					value='<%=libraryBranch.getBranchName()%>'></th>
			</tr>

			<tr>
				<th>Enter Library Branch Address: <input class="form-control" placeholder="Address" type="text" name="branchAddress"
					value='<%=libraryBranch.getBranchAddress()%>'></th>
			</tr>
		</table>

		<input type="hidden" name="branchId" value=<%=libraryBranch.getBranchId()%>>
		<input type="submit" value="Edit Library Branch" class="btn btn-success"/>
	</form>
</div>