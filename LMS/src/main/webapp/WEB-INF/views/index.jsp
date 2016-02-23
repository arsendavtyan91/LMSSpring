<%@include file="include.html"%>
<div class="container theme-showcase" role="main">

	<div class="jumbotron">
		<h1>GCIT Management System</h1>
		<p>Welcome to GCIT Library Management System.</p>
	</div>
</div>
        <div class="container">
            <div class="box first">
                <div class="row">
                    <div class="col-md-4 col-sm-6">
                        <div class="center">
                            <i class="icon-apple icon-md icon-color1"></i>
                            <h4 align="center"><a href = "admin" class="btn btn-info btn-lg" role="button">Administrator</a><br></h4>
                            <blockquote>
                            <p>If you are an <abbr title="You can Do everything!">Administrator</abbr>, you can add, update, delete Authors, Books, Borrowers, Branches you can do anything you want. Even you can override the due date for Borrowers. It's Nice Right?</p>
                            </blockquote>
                        </div>
                    </div><!--/.col-md-4-->
                    <div class="col-md-4 col-sm-6">
                        <div class="center">
                            <i class="icon-android icon-md icon-color2"></i>
                            <h4 align="center"><a href = "borrowerModal" data-toggle="modal" data-target="#borrowerModal" class="btn btn-info btn-lg" role="button">Borrower</a><br></h4>
                            <blockquote>
                            <p>Being <abbr title="You are in the middle, because you are Customer!">Borrower</abbr> you can Choose the Library Branches And check out some Books. You'll have 7 days to return it. You can even look the books you loan.</p>
                            </blockquote>
                        </div>
                    </div><!--/.col-md-4-->
                    <div class="col-md-4 col-sm-6">
                        <div class="center">
                            <i class="icon-windows icon-md icon-color3"></i>
                            <h4 align="center"><a href = "librarian" class="btn btn-info btn-lg" role="button">Librarian</a><br></h4>
                            <blockquote>
                            <p>If you're a Librarian you can edit the name of the current <abbr title="I don't know Why?!">Branch</abbr> you manage. And you can Choose the books in the Library and you can add or delete copies in current Branch</p>
                            </blockquote>
                        </div>
                    </div><!--/.col-md-4-->
                                    </div><!--/.row-->
            </div><!--/.box-->
        </div><!--/.container-->
<footer>
  <p>&#174; Arsen Davtyan for GCIT</p>
</footer>
<div id="borrowerModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>


