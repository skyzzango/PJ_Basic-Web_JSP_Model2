package type03.controller.board;

import com.google.gson.Gson;
import type03.action.Action;
import type03.dao.BoardDao;
import type03.dto.BoardDto;
import type03.util.MyUtil;
import type03.util.Script;
import type03.websocket.BroadSocket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BoardUpdateProcAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Run: " + getClass().getName());

		BoardDto board = new BoardDto();
		board.setNum(Integer.parseInt(request.getParameter("num")));
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));

		String url = "board?cmd=board_view&num=" + board.getNum();

		BoardDao boardDao = new BoardDao();

		boolean change;
		List<BoardDto> hotPost1 = boardDao.getHotBoard();
		int result = boardDao.updateBoard(board);
		List<BoardDto> hotPost2 = boardDao.getHotBoard();

		change = MyUtil.getBoardChange(hotPost1, hotPost2);

		if (change) {
			Gson gson = new Gson();
			String hotPostJson = gson.toJson(hotPost2);
			BroadSocket.serverMessage(hotPostJson);
		}

		if (result == 1) {
			Script.moveUrl(response, "글 수정 성공", url);
		} else if (result == -1) {
			System.out.println("Error: " + getClass().getName());
			Script.moveBack(response, "DB Error: 이전 페이지로 이동합니다.");
		}

	}
}
