<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");
	Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
	Borrower borrower = service.getOneBorrower(cardNo);
%>

<div class="modal-body">
	<form action="editBorrowerSubmit" method="post">

		<table class="table">

			<tr>
				<th>Enter Borrower Name: <input class="form-control" type="text" name="borrowerName"
					value='<%=borrower.getName()%>'></th>
			</tr>

			<tr>
				<th>Enter Borrower Address: <input class="form-control" type="text" name="borrowerAddress"
					value='<%=borrower.getAddress()%>'></th>
			</tr>
			<tr>
				<th>Enter Borrower Phone: <input class="form-control" type="text" name="borrowerPhone"
					value='<%=borrower.getPhone()%>'></th>
			</tr>
		</table>

		<input type="hidden" name="cardNo" value=<%=borrower.getCardNo()%>>
		<input type="submit" value="Edit Borrower" class="btn btn-success"/>
	</form>
</div>