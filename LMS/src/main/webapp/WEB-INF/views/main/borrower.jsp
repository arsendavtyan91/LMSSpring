<%@include file="../include.html"%>
<script>


setTimeout(function() {
    $('#checkOut').fadeOut('fast');
}, 3000); 

setTimeout(function() {
    $('#error').fadeOut('fast');
}, 3000); 

setTimeout(function() {
    $('#return').fadeOut('fast');
}, 3000); 
</script>

<h1 align="center">Welcome Dear Borrower</h1>
<form id="formName" action="options" method="POST">

	<br>

	<div class="col-xs-3">
		<label for="usr">Enter your Card Number:</label> 
		<input
			placeholder="Card Number" type="text" class="form-control"
			id="cardNo" name="cardNo">
			<br> 
			<input type="submit"
			class="btn btn-info" value="Submit">
			<h3><p id="error"><font color="red">${message}</font></p></h3>
			<h3 align = "center"><p id="checkOut"><font color="green">${messageBorrowerCheckOut}</font></p></h3>
			<h3 align = "center"><p id="return"><font color="green">${borrowerReturnBook}</font></p></h3>
	</div>
</form>