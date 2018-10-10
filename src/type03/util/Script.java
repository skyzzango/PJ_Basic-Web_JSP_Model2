package type03.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Script {

	public static void moveBack(HttpServletResponse response, String msg) {
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + msg + "')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		} catch (IOException e) {
			MyUtil.showException(Script.class.getName(), "moveBack", e);
		}
	}

	public static void moveUrl(HttpServletResponse response, String msg, String url) {
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + msg + "')");
			out.println("location.href='" + url + "'");
			out.println("</script>");
			out.close();
		} catch (Exception e) {
			MyUtil.showException(Script.class.getName(), "moveUrl", e);
		}
	}

	public static void alert(HttpServletResponse response, String msg) {
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + msg + "')");
			out.println("</script>");
			out.close();
		} catch (IOException e) {
			MyUtil.showException(Script.class.getName(), "moveBack", e);
		}
	}

}
