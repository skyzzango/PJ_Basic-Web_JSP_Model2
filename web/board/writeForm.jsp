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
	<title>글 쓰기</title>
</head>

<body>

<%@include file="/view/partials/nav.jsp" %>


<div class="container">

	<div class="starter-template">
		<div class="row">
			<!-- Blog Entries Column -->
			<div class="col-md-12 my-order">
				<div class="content-section">
					<form name="w_form" action="${pageContext.request.contextPath}/board?cmd=board_write" method="post"
					      enctype="multipart/form-data">
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
								<label for="file" class="form-control-label">파일 첨부 </label>
								<div class="custom-file">
									<input type="file" class="custom-file-input" id="file" name="file">
									<label class="custom-file-label" for="file">Choose file</label>
								</div>
							</div>
							<div class="form-group">
								<label for="content" class="form-control-label">Content </label>
								<textarea id="content" name="content" style="width: 100%" rows="10"></textarea>
							</div>
							<div class="form-group">
								<button class="btn btn-outline-info" type="button" onclick="submitContents(this)">
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
<!-- Smart Editor -->
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"></script>
<!-- Naver Smart Editor 2 -->
<script>
	var form = document.w_form;
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
		sSkinURI: "<%=request.getContextPath()%>/editor/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});

	// submit
	function submitContents(elClickedObj) {
		// 에디터의 내용이 textarea에 적용된다.
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", [ ]);
		var con = document.w_form.content;
		con.value = document.getElementById("content").value;

		try {
			elClickedObj.form.submit();
		} catch(e) {

		}
	}

	// textArea에 이미지 첨부
	function pasteHTML(filepath){
		var sHTML = '<img src="<%=request.getContextPath()%>/editor/upload/'+ filepath + '">';
		oEditors.getById["content"].exec("PASTE_HTML", [ sHTML ]);
	}
</script>


</body>
</html>