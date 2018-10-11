package type03.controller.board;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import type03.action.Action;
import type03.dao.BoardDao;
import type03.dto.BoardDto;
import type03.util.Script;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class BoardWriteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		System.out.println("Run: " + getClass().getName());
		String url = "index.jsp";

		String savePath = "fileUpload/upload"; // 다운받는 경로 설정
		int uploadFileSizeLimit = 5 * 1024 * 1024; // 파일 최대크기 5M으로 제한
		String encType = "UTF-8"; // char 인코딩 방식

		////////////////////////////////////////////////////////////////
//		String uploadPath = request.getRealPath("/editor/upload/");
//		System.out.println("uploadPath: " + uploadPath);
//		MultipartRequest multi = new MultipartRequest(
//				request,
//				uploadPath,
//				uploadFileSizeLimit,
//				encType,
//				new DefaultFileRenamePolicy()
//		);
//		Enumeration files = multi.getFileNames();
//		String file = (String) files.nextElement();
//		String fileName1 = multi.getFilesystemName(file);
//		System.out.println("fileName1: " + fileName1);
		////////////////////////////////////////////////////////////////

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("isMultipart: " + isMultipart);

		Map<String, String[]> parameterMap = request.getParameterMap();
		for (String key : parameterMap.keySet()) {
			System.out.println(key + ": " + request.getParameter(key));
		}

		String id;
		request.getSession().setAttribute("id", "admin@admin.com");
		HttpSession session = request.getSession();
		if (session.getAttribute("id") != null) {
			id = (String) session.getAttribute("id");
			int result = 1;
			if (result != 1) {
				Script.moveUrl(response, "인증 후 이용 가능합니다.", "member?cmd=member_update");
			} else {
				BoardDto board = new BoardDto();
				board.setId(id);
				board.setTitle(request.getParameter("title"));
				board.setContent(request.getParameter("content"));

				////////////////////////////////////////////////////////////////
				ServletContext context = request.getSession().getServletContext();
				String uploadFilePath = context.getRealPath(savePath);
				System.out.println("uploadFilePath: " + uploadFilePath);
				MultipartRequest multi1 = new MultipartRequest(
						request, // request 객체
						uploadFilePath, // 서버 상의 실제 데이터
						uploadFileSizeLimit, // 최대 업로드 파일크기
						encType, // 인코딩 타입
						new DefaultFileRenamePolicy()
				);
				// UploadFile 이름은 input 태그의 name 과 동일한 이름을 사용한다.
				String fileName = multi1.getFilesystemName("file");
				System.out.println("fileName1: " + fileName);
				////////////////////////////////////////////////////////////////

				BoardDao boardDao = new BoardDao();
				int insert = boardDao.insertBoard(board);
				if (insert == 1) {
					Script.moveUrl(response, "글 등록 완료!!", url);
				} else {
					System.out.println("Error: " + getClass().getName());
					Script.moveBack(response, "DB Error: 이전 페이지로 이동합니다.");
				}
			}
		} else {
			Script.moveUrl(response, "로그인 후 사용 이용 가능합니다.", "member/loginForm.jsp");
		}
	}
}
