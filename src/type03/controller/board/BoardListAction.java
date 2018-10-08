package type03.controller.board;

import type03.action.Action;
import type03.dao.BoardDao;
import type03.dto.BoardDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BoardListAction implements Action {
	private static String naming = "BoardListAction: ";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Run: " + naming);
		String url = "main.jsp";

		int pageNum = 0;
		if (request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		BoardDao board = new BoardDao();
		List<BoardDto> boardList = board.select_paging(pageNum);
		List<BoardDto> hotBoard = board.hotBoard();
	}
}
