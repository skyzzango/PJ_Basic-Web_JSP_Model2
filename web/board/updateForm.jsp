<%--
  Created by IntelliJ IDEA.
  User: skyzz
  Date: 2018-10-11
  Time: 오후 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
	<%@include file="/view/partials/head.jsp" %>
	<title>글 수정</title>
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
						<input type="hidden" name="id" value="${board.id}"/>
						<input type="hidden" name="num" value="${board.num}"/>

						<fieldset class="form-group">
							<legend class="border-bottom mb-4">Update Post</legend>
							<div class="form-group">
								<label for="title" class="form-control-label">Title</label>
								<input class="form-control form-control-lg" id="title" type="text" name="title"
								       value="${board.title}" autofocus>
							</div>
							<div class="form-group">
								<label for="file" class="form-control-label">파일 첨부 </label>
								<div class="custom-file">
									<input type="file" class="custom-file-input" id="file" name="file">
									<label class="custom-file-label" for="file">Choose file</label>
								</div>
							</div>
							<div class="form-group">
								<label for="textAreaContent" class="form-control-label">Content </label>
								<textarea id="textAreaContent" name="content" rows="10">
									${board.content}
								</textarea>
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
<!-- Smart Editor -->
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"></script>
<script>
	var form = document.w_form;
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "textAreaContent",
		sSkinURI: "<%=request.getContextPath()%>/editor/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});

	// submit
	function submitContents(elClickedObj) {
		// 에디터의 내용이 textarea에 적용된다.
		oEditors.getById["textAreaContent"].exec("UPDATE_CONTENTS_FIELD", [ ]);
		var con = document.w_form.content;
		con.value = document.getElementById("textAreaContent").value;

		try {
			elClickedObj.form.submit();
		} catch(e) {

		}
	}

	// textArea에 이미지 첨부
	function pasteHTML(filepath){
		var sHTML = '<img src="<%=request.getContextPath()%>/editor/upload/'+ filepath + '">';
		oEditors.getById["textAreaContent"].exec("PASTE_HTML", [ sHTML ]);
	}
</script>

</body>
</html>
