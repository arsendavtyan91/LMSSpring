<%@include file="../include.html"%>

<form action="addLibraryBranchSubmit" method="post">
	<h2>Enter Library Branch Details</h2>
	<table class="table">
		<tr>
			<td>Enter Branch Name:</td>
			<td><input class="form-control" placeholder="Name" type="text" name="branchName" /></td>
		</tr>
		<tr>
			<td>Enter Branch Address:</td>
			<td><input class="form-control" placeholder="Address" type="text" name="branchAddress" /></td>
		</tr>
	</table>
	<input type="submit" value= "Add Library Branch" class="btn btn-success"'>
</form>