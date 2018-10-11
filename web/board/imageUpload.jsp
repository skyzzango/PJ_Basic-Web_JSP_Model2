<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: skyzz
  Date: 2018-10-10
  Time: 오후 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
	// 이미지 업로드할 경로
	String uploadPath = request.getContextPath() + "/upload/";
	System.out.println("uploadPath: " + uploadPath); // path 를 출력해서 확인(fileFolder 없으면 생성해주자!!!)
	int size = 10 * 1024 * 1024;  // 업로드 사이즈 제한 10M 이하

	String fileName = ""; // 파일명

	try {
		// 파일업로드 및 업로드 후 파일명 가져옴
		MultipartRequest multi = new MultipartRequest(request, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
		Enumeration files = multi.getFileNames();
		String file = (String) files.nextElement();
		fileName = multi.getFilesystemName(file);
		originFile = multi.getOriginalFileName(file);
	} catch (Exception e) {
		e.printStackTrace();
	}

	// 업로드된 경로와 파일명을 통해 이미지의 경로를 생성
	String uploadPath1 = "/editor/upload/" + fileName;

	// 생성된 경로를 JSON 형식으로 보내주기 위한 설정
	JSONObject jobj = new JSONObject();
	jobj.put("url", uploadPath1);

	response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
	out.print(jobj.toJSONString());
%>
