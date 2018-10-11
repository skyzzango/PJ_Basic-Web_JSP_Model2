package type03.controller.board;

import type03.action.Action;
import type03.dao.BoardDao;
import type03.dto.BoardDto;
import type03.util.Script;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BoardUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Run: " + getClass().getName());
		String url = "board/updateForm.jsp";
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDao boardDao = new BoardDao();
		BoardDto board = boardDao.getBoard(num);

		if (board == null) {
			System.out.println("Error: " + getClass().getName());
			Script.moveBack(response, "DB Error: 이전 페이지로 이동합니다.");
		} else {
			request.setAttribute("board", board);
			RequestDispatcher dis = request.getRequestDispatcher(url);
			dis.forward(request, response);
		}
	}
}
