<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdministratorService"%>

<h3>Current Due Date is <%=request.getParameter("dueDate")%></h3>
<div class="modal-body">
<form action="overrideDueDateSubmit" method="post" >
<label for="usr">Enter New Due Date like yyyy/mm/dd</label>
<input class="form-control" type="text" name="dueDate"><br/>

<input type = "hidden" name = "cardNo" value = <%=request.getParameter("cardNo")%>>
<input type = "hidden" name = "bookId" value = <%=request.getParameter("bookId")%>>
<input type = "hidden" name = "branchId" value = <%=request.getParameter("branchId")%>>

<button type="submit" class="btn btn-success">Override Due Date</button>

<script>
  $(document).ready(function() {
    $("#datepicker").datepicker();
  });
  </script>
</form>
</div>