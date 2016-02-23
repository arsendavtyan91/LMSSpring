<%@include file="../include.html"%>


<style>
.myCenter {
	display: block;
	margin: 0 auto;
	text-align: center;
	margin-top: 10%;
}

.but {
	width: 20%;
}
</style>
<h1 align="center">Welcome Dear ${bName}</h1>



<div class="myCenter">
<h3 align="center">Choose your option</h3>
<br>
	<form action="checkOutBookBranches" method="POST">
		<input type="hidden" value=<%=request.getParameter("cardNo")%>
			name="cardNo"> <input class="but btn btn-info btn-lg"
			type="submit" value="Check Out Book" name="submit">
	</form>
	<br>
	<form action="returnBook" method="POST">
		<input type="hidden" value=<%=request.getParameter("cardNo")%>
			name="cardNo"> <input class="but btn btn-info btn-lg"
			type="submit" value="Return Book" name="submit">
	</form>

</div>
