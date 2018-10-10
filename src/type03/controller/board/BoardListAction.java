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
import java.io.IOException;
import java.util.List;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Run: " + getClass().getName());
		String url = "main.jsp";

		int pageNum = 0;
		if (request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		BoardDao boardDao = new BoardDao();
		List<BoardDto> boardList = boardDao.getPageBoard(pageNum);
//		List<BoardDto> hotBoard = boardDao.getHotBoard();

		if (boardList == null) {
			System.out.println("Error: " + getClass().getName());
			Script.moveBack(response, "DB Error: 이전 페이지로 이동합니다.");
		} else {
			for (BoardDto board : boardList) {
				System.out.print("Before Board: " + board);
				board.setTitle(MyUtil.previewBoardTitle(board.getTitle()));
				board.setContent(MyUtil.previewBoardContent(board.getContent()).replaceAll("\n", "<br>"));
				System.out.println("After Board: " + board);
			}
		}

		request.setAttribute("boardList", boardList);
//		request.setAttribute("hotBoard", hotBoard);
		request.setAttribute("pageNum", pageNum);

//		BroadSocket.serverMessage("메인 페이지 갱신됨");

		RequestDispatcher dis = request.getRequestDispatcher(url);
		dis.forward(request, response);
	}
}
