<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: skyzz
  Date: 2018-09-20
  Time: 오전 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
	<a class="navbar-brand" href="${pageContext.request.contextPath}/">Rong Blog</a>
	<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
	        data-target="#navb" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navb">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active">
				<a class="nav-link" href="${pageContext.request.contextPath}/">Home <span
						class="sr-only">(current)</span></a>
			</li>
			<li class="nav-item">
				<a class="nav-link disabled" href="#">Link</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="<%=request.getContextPath()%>/board/writeForm.jsp">New Post</a>
			</li>
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown"
				   aria-haspopup="true" aria-expanded="false">Dropdown</a>
				<div class="dropdown-menu" aria-labelledby="dropdown01">
					<a class="dropdown-item" href="<c:url value="/type01/post/"/>">Type1 - 게시판</a>
					<a class="dropdown-item" href="<c:url value="/type02/board/"/>">Type2 - 게시판</a>
					<a class="dropdown-item" href="${pageContext.request.contextPath}/board">Type3 - 게시판</a>
					<a class="dropdown-item" href="#">Something</a>
				</div>
			</li>
		</ul>

		<div class="btn-inline my-2 my-lg-0">
			<c:choose>
				<c:when test="${empty sessionScope.member}">
					<button class="btn btn-outline-primary mr-sm-2" onclick="location.href='/type03/member/login.jsp'">
						로그인
					</button>
					<button class="btn btn-outline-primary mr-sm-3" onclick="location.href='/type03/member/signup.jsp'">
						회원가입
					</button>
				</c:when>
				<c:otherwise>
					<button class="btn btn-primary mr-sm-2" onclick="location.href='/type03/member/logout_Action.jsp'">
						로그아웃
					</button>
					<button class="btn btn-primary mr-sm-3" onclick="location.href='/type03/member/update_Form.jsp'">
						정보수정
					</button>
				</c:otherwise>
			</c:choose>
		</div>

		<form class="form-inline my-2 my-lg-0" action="<c:url value="/type03/board/search.jsp"/>" method="get">
			<input class="form-control mr-sm-2" name="search" type="search" placeholder="Search" required>
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		</form>

	</div>
</nav>
