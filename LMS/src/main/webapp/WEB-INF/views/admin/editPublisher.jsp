<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>

<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdministratorService service = (AdministratorService) ac.getBean("adminService");
	
	Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));	
	Publisher publisher = service.getOnePublisher(publisherId);
%>

<div class="modal-body">
	<form action="editPublisherSubmit" method="post">

		<table class="table">

			<tr>
				<th>Enter Publisher Name: <input type="text" name="publisherName"
					value='<%=publisher.getPublisherName()%>'></th>
			</tr>

			<tr>
				<th>Enter Publisher Address: <input type="text" name="publisherAddress"
					value='<%=publisher.getPublisherAddress()%>'></th>
			</tr>
			<tr>
				<th>Enter Publisher phone: <input type="text" name="publisherPhone"
					value='<%=publisher.getPublisherPhone()%>'></th>
			</tr>
		</table>

		<input type="hidden" name="publisherId" value=<%=publisher.getPublisherId()%>>
		<input type="submit" value="Edit Publisher" class="btn btn-success"/>
	</form>
</div>