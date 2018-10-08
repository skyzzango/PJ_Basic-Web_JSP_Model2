<%--
  Created by IntelliJ IDEA.
  User: skyzz
  Date: 2018-10-08
  Time: 오후 8:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
	<title>$Title$</title>
</head>

<body>



<div class="container">
	
	<div class="starter-template">
		<h1>$END$</h1><br>
		<a href="insert.do">삽입</a>
		<a href="<%=request.getContextPath()%>/select.do">선택</a>
		<a href="update.do">수정</a>
		<a href="<%=request.getContextPath()%>/delete.do">삭제</a>
	</div>


</div><!-- /.container -->



</body>
</html>
