package type03.util;

import type03.dto.BoardDto;

import java.util.List;

public class MyUtil {

	//HotPost 변경유무 확인 (데이터 조회, 수정, 삭제시 호출됨)
	public static boolean getBoardChange(List<BoardDto> hotPost1, List<BoardDto> hotPost2) {
		boolean change = false;
		for (int i = 0; i < hotPost1.size(); i++) {
			if (hotPost1.get(i).getNum() != hotPost2.get(i).getNum()) change = true;
			if (!hotPost1.get(i).getTitle().equals(hotPost2.get(i).getTitle())) change = true;
			if (hotPost1.get(i).getReadcount() != hotPost2.get(i).getReadcount()) change = true;
		}
		return change;
	}

	// 글 제목 미리보기
	public static String previewBoardTitle(String title) {
		String removeContent = removeTag(title);
		if (removeContent.length() > 20) {
			removeContent = removeContent.substring(0, 20) + ".....";
		}
		return removeContent;
	}

	// 글 내용 미리보기
	public static String previewBoardContent(String content) {
		String removeContent = removeTag(content);
		if (removeContent.length() > 100) {
			removeContent = removeContent.substring(0, 100) + ".....";
		}
		return removeContent;
	}

	//모든 HTML 태그 제거
	public static String removeTag(String html) {
		html = html.replaceAll("&nbsp;", " ");
		return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	}

	//크로스 사이트 스크립트 공격 방어
	public static String xssDefense(String content) {
		return content.replaceAll("&", "&amp;").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">",
				"&gt;")
				.replaceAll("\"", "&quot;").replaceAll("\'", "&#x27;").replaceAll("\\\\", "&#x5c;");
	}

	public static void showException(String aClass, String method, Exception e) {
		System.err.println("Exception: " + aClass + " Function(" + method + ") Something Problem!!");
		System.out.println(e.getMessage());
		e.printStackTrace();
	}

}
