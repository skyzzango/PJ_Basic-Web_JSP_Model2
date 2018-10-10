package type03.controller.board;

import type03.action.Action;
import type03.dao.BoardDao;
import type03.dto.BoardDto;
import type03.util.MyUtil;
import type03.util.Script;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BoardWriteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Run: " + getClass().getName());
		String url = "index.jsp";

		String id;
		HttpSession session = request.getSession();
		if (session.getAttribute("id") != null) {
			id = (String) session.getAttribute("id");
			int result = 1;
			if (result != 1) {
				Script.moveUrl(response, "인증 후 이용 가능합니다.", "member?cmd=member_update");
			} else {
				BoardDto board = new BoardDto();
				board.setId(request.getParameter("id"));
				board.setTitle(request.getParameter("title"));
				board.setContent(request.getParameter("content"));

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
