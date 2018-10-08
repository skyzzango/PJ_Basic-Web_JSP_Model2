package type03.controller.board;

import type03.action.Action;
import type03.action.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String naming = "BoardController: ";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String cmd = null;
		if (request.getParameter("cmd") != null) {
			cmd = request.getParameter("cmd");
		}

		System.out.println(naming + cmd);

		ActionFactory af = ActionFactory.getInstance();
		Action action = af.getAction(cmd);
		if (action != null) action.execute(request, response);

	}
}
