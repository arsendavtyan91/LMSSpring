<div class="modal-body">
<h1 align="center">Welcome Dear Borrower</h1>
<form id="formName" action="options" method="POST">

	<br>

		<label for="usr">Enter your Card Number:</label> 
		<input
			placeholder="Card Number" type="text" class="form-control"
			id="cardNo" name="cardNo">
			<br> 
			<input type="submit"
			class="btn btn-info" value="Submit">
			<h3><p><font color="red">${message}</font></p></h3>
</form>
</div>
<div id="borrowerModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
