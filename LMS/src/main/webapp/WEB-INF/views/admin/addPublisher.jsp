<%@include file="../include.html"%>

<form action="addPublisherSubmit" method="post">
	<h2>Enter Publisher Details</h2>
	<table class="table">
		<tr>
			<td>Enter Publisher Name:</td>
			<td><input type="text" name="publisherName" placeholder="Name"/></td>
		</tr>
		<tr>
			<td>Enter Publisher Address:</td>
			<td><input type="text" name="publisherAddress" placeholder="Address"/></td>
		</tr>
		<tr>
			<td>Enter Publisher Phone:</td>
			<td><input type="text" name="publisherPhone" placeholder="Phone"/></td>
		</tr>
	</table>
	<input type="submit" value="Add Publisher" class="btn btn-success">
</form>