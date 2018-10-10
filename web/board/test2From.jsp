<%--
  Created by IntelliJ IDEA.
  User: skyzz
  Date: 2018-10-10
  Time: 오후 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
	<%@include file="/view/partials/head.jsp" %>
	<title>Title</title>
</head>

<body>

<%@include file="/view/partials/nav.jsp" %>


<div class="container">

	<div class="starter-template">
		<div class="row">
			<!-- Blog Entries Column -->
			<div class="col-md-12 my-order">
				<div class="content-section">
					<form name="w_form" action="<%=request.getContextPath()%>/board?cmd=board_write" method="post">
						<!-- filepath : 이미지업로드 경로 -->
						<input type="hidden" name="filepath" value="/editor/upload/"/>
						<input type="hidden" name="id" value="${sessionScope.id}"/>

						<fieldset class="form-group">
							<legend class="border-bottom mb-4">New Post</legend>
							<div class="form-group">
								<label for="title" class="form-control-label">Title</label>
								<input class="form-control form-control-lg" id="title" type="text" name="title"
								       autofocus>
							</div>
							<div class="form-group">
								<label for="summernote" class="form-control-label">Content </label>
								<textarea id="summernote" name="content"></textarea>
							</div>
							<div class="form-group">
								<button class="btn btn-outline-info" type="button" onclick="submitContents(this)">
									Update
								</button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div><!-- ./row -->

	</div>

</div><!-- /.container -->


<%@include file="/view/partials/script.jsp" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.js"></script>
<script>
	$('#summernote').summernote({
		placeholder: 'Hello bootstrap 4',
		tabsize: 2,
		height: 100
	});
</script>

</body>
</html>