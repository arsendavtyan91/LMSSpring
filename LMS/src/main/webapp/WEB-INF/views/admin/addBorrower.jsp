<%@include file="../include.html"%>

<form action="addBorrowerSubmit" method="post">
	<h2>Please Enter Borrower Details</h2>
	<table class="table">
		<tr>
			<td>Enter Borrower Name:</td>
			<td><input class="form-control" type="text" name="borrowerName" placeholder="Name"/></td>
		</tr>
		<tr>
			<td>Enter Borrower Address:</td>
			<td><input class="form-control" type="text" name="borrowerAddress" placeholder="Address"/></td>
		</tr>
		<tr>
			<td>Enter Borrower Phone:</td>
			<td><input class="form-control" type="text" name="borrowerPhone" placeholder="Phone"/></td>
		</tr>
	</table>
	<input type="submit" value="Add Borrower" class="btn btn-success">
</form>