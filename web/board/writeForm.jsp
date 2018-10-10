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
								<progress></progress>
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

<!-- include summernote css/js -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.js"></script>

<!-- summernote Editor -->
<script type="text/javascript">
	// summernote를 사용하기 위한 선언
	$('#summernote').summernote({
		lang     : 'ko-KR',
		height   : 300,
		minHeight: null,
		maxHeight: null,
		focus    : true,
		onImageUpload: function (files, editor, welEditable) {
			for (var i = files.length - 1; i >= 0; i--) {
				sendFile(files[i], this);
			}
		}
	});

	/* summernote에서 이미지 업로드시 실행할 함수 */
	function sendFile(file, el) {
		event.preventDefault();
		var files = event.dataTransfer.files;
		var file = files[0];
		console.log(file);
		var data = new FormData();
		data.append("file", file);
		$.ajax({
			data: data,
			type: 'POST',
			dataType: 'text',
			xhr: function() {
				var myXhr = $.ajaxSettings.xhr();
				if (myXhr.upload) myXhr.upload.addEventListener('progress',progressHandlingFunction, false);
				return myXhr;
			},
			url: 'imageUpload.jsp',
			cache: false,
			contentType: false,
			processData: false,
			success: function(data) {
				editor.insertImage(welEditable, url);
				$(el).summernote('editor.insertImage',"/displayFile?fileName=" + data);
			}
		});
	}

	// update progress bar
	function progressHandlingFunction(e) {
		if (e.lengthComputable) {
			$('progress').attr({value: e.loaded, max: e.total});
			// reset progress on complete
			if (e.loaded == e.total) {
				$('progress').attr('value', '0.0');
			}
		}
	}
</script>

</body>
</html>