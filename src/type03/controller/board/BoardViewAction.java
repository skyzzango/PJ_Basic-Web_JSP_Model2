package type03.controller.board;

import com.google.gson.Gson;
import type03.action.Action;
import type03.dao.BoardDao;
import type03.dto.BoardDto;
import type03.util.MyUtil;
import type03.util.Script;
import type03.websocket.BroadSocket;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "board/viewPage.jsp";

		BoardDao boardDao = new BoardDao();
//		ReBoardDAO rdao = new ReBoardDAO();

		int num = Integer.parseInt(request.getParameter("num"));

		boolean change;
		List<BoardDto> hotPost1 = boardDao.getHotBoard();
		int result = boardDao.readCount(num);
		List<BoardDto> hotPost2 = boardDao.getHotBoard();

		change = MyUtil.getBoardChange(hotPost1, hotPost2);

		if(change) {
			Gson gson = new Gson();
			String hotPostJson = gson.toJson(hotPost2);
			BroadSocket.serverMessage(hotPostJson);
		}

		if (result != 1) {
			System.out.println("Error: " + getClass().getName());
			Script.moveBack(response, "DB Error: 이전 페이지로 이동합니다.");
		}

		BoardDto board = boardDao.getBoard(num);
		board.setContent(board.getContent().replaceAll("\n", "<br>"));

//		ArrayList<ReBoardVO> reboards = rdao.select_all(num);

		if (board == null) {
			System.out.println("Error: " + getClass().getName());
			Script.moveBack(response, "DB Error: 이전 페이지로 이동합니다.");
		} else {
			request.setAttribute("board", board);
//			request.setAttribute("reboards", reboards);
			RequestDispatcher dis = request.getRequestDispatcher(url);
			dis.forward(request, response);
		}
	}
}
