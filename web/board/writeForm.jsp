<%--
  Created by IntelliJ IDEA.
  User: skyzz
  Date: 2018-10-10
  Time: 오전 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
	<%@include file="/view/partials/head.jsp" %>
	<title>글쓰기</title>
</head>

<body>

<%@include file="/view/partials/nav.jsp" %>


<div class="container">

	<div class="starter-template">
		<div class="row">
			<!-- Blog Entries Column -->
			<div class="col-md-12 my-order">
				<div class="content-section">
					<form name="w_form" action="${pageContext.request.contextPath}/" method="post"
					      enctype="multipart/form-data">
						<!-- filepath : 이미지업로드 경로 -->
						<input type="hidden" name="filepath" value="/upload/"/>
						<input type="hidden" name="id" value="${sessionScope.id}"/>

						<fieldset class="form-group">
							<legend class="border-bottom mb-4">New Post</legend>
							<div class="form-group">
								<label for="title" class="form-control-label">Title</label>
								<input class="form-control form-control-lg" id="title" type="text" name="title"
								       autofocus>
							</div>
							<div class="form-group">
								<label for="editor2" class="form-control-label">Content </label>
								<textarea id="editor2" name="editor2" rows="10"></textarea>
								<progress></progress>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary">
									Submit
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
<script src="https://cdn.ckeditor.com/4.10.1/standard-all/ckeditor.js"></script>
<script>
	CKEDITOR.replace( 'editor2', {
		extraPlugins: 'uploadimage,image2',
		height: 300,

		// Upload images to a CKFinder connector (note that the response type is set to JSON).
		uploadUrl: 'https://sdk.ckeditor.com/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files&responseType=json',

		// Configure your file manager integration. This example uses CKFinder 3 for PHP.
		filebrowserBrowseUrl: 'https://sdk.ckeditor.com/ckfinder/ckfinder.html',
		filebrowserImageBrowseUrl: 'https://sdk.ckeditor.com/ckfinder/ckfinder.html?type=Images',
		filebrowserUploadUrl: 'https://sdk.ckeditor.com/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files',
		filebrowserImageUploadUrl: 'https://sdk.ckeditor.com/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images',

		// The following options are not necessary and are used here for presentation purposes only.
		// They configure the Styles drop-down list and widgets to use classes.

		stylesSet: [
			{ name: 'Narrow image', type: 'widget', widget: 'image', attributes: { 'class': 'image-narrow' } },
			{ name: 'Wide image', type: 'widget', widget: 'image', attributes: { 'class': 'image-wide' } }
		],

		// Load the default contents.css file plus customizations for this sample.
		contentsCss: [ CKEDITOR.basePath + 'contents.css', 'https://sdk.ckeditor.com/samples/assets/css/widgetstyles.css' ],

		// Configure the Enhanced Image plugin to use classes instead of styles and to disable the
		// resizer (because image size is controlled by widget styles or the image takes maximum
		// 100% of the editor width).
		image2_alignClasses: [ 'image-align-left', 'image-align-center', 'image-align-right' ],
		image2_disableResizer: true
	} );
</script>


</body>
</html>