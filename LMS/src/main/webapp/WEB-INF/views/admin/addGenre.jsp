<%@include file="../include.html"%>

<form action="addGenreSubmit" method="post">
	<h2>Enter Genre Details</h2>
	<p>
		<label for="usr">Enter Genre Name:</label> 
		<input placeholder="Enter Genre Name" class="col-md-3 form-control" type="text" name="genreName" />
	</p>
	<input type="submit" value="Add New" class="btn btn-success">
</form>