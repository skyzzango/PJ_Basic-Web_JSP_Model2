package type03.action;

import type03.dao.BoardDao;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Beans extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 실행해야 할 모든 Service 객체를 저장해 놓는 Map
	public static Map<String, Action> serviceBeans = new HashMap<>();

	// 실행해야 할 모든 Dao 객체를 저장해 놓는 Map
	public static Map<String, Object> daoBeans = new HashMap<>();
	// Path 설정을 위해서 기본 설정값을 선언해놓는다.
	// 설정한 Path 에 모든 jsp 파일이 들어가야 한다.
	public static String pre = "/WEB-INF/views/";
	public static String suf = ".jsp";

	// 실행 할 Service 를 받아가는 메서드
	public static Action getService(String uri) {
		return serviceBeans.get(uri); // get 메서드 는 HashMap 안에 있는 메서드
	} // end of getService()

	// 실행 할 Dao 를 받아가는 메서드
	public static Object getDao(String uri) {
		return daoBeans.get(uri);
	} // end of getDao()

	// request 받아서 URI 를 리턴하는 프로그램 작성. uri 를 정제하는 역할을 한다.
	// 모든 Controller 에서는 따로 작성하지 않고 받아가서 처리한다.
	public static String getURI(HttpServletRequest request) {
		// request 에서 uri 를 받아서 servletPath 에서부터 잘라낸 값을 리턴한다.
		// 여기서 /board/list, /notice/view 등의 형태로 바뀐다.
		String uri = request.getRequestURI();
		return uri.substring(uri.indexOf(request.getServletPath()));
	} // end of getURI()

	// 정제한 문자열을 forward 로 연결할 jsp 로 만들어주는 메서드.
	// 패스는 /WEB-INF/views/board/list.jsp 등으로 설정된다.
	public static String getJsp(String uri) {
		return pre + uri.substring(0, uri.lastIndexOf('.')) + suf;
	} // end of getJsp()

	/**
	 * @see Servlet#init(ServletConfig) 여기가 제일 먼저 실행이 되서 모든 객체를 생성한다.
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub

		// ========== dao 생성해서 저장하는 처리문.(모든 Dao 프로그램을 다 생성해 놓는다.)
		daoBeans.put("boardDao", new BoardDao()); // 키가 boardDao, 값이 BoardDao()객체이다.

		// ========== service 를 생성해서 저장하는 프로그램 작성
		// web.xml 에 servlet 태그 안에 init-param 태그로 정의되어 있는 정보를 받는다.
		String configFile = getInitParameter("configFile"); // web.xml 에 정의되어 있는 command.properties 의 name 이다.
		// configFile(command.properties 파일)컴퓨터 절대 위치를 체크한다.
		String configFilePath = getServletContext().getRealPath(configFile); // 추후 파일을 읽어서 실행시킬 것이다.
		// key(String) = value(String) 값을 받아올 수 있는 객체 Properties 를 이용하여 문자열로 받아낸다.
		// configFile(command.properties 파일)을 읽어오려는 준비
		Properties prop = new Properties();
		try (FileReader fis = new FileReader(configFilePath)) { // try(자원선언) 을 하면 따로 close()로 반환을 할 필요가 없다.
			// properties 파일에서부터 key=value 형식으로 저장된 모든 정보를 읽어온다.
			// key: String, value: String 이기 때문에 지네릭스가 필요없다.
			prop.load(fis);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("파일 읽기 오류");
		} // end of try - catch;

		// =====prop.load(fis)로 읽어온 command.properties 파일을 이용하여 객체를 자동생성하게 만든다.
		// prop 객체에서 key -> set -> iterator : 앞의 순서로 모든 객체를 생성하게 만든다.
		// prop 키를 입력해서 생성해야 할 객체이름을 꺼낸다.
		for (Object key : prop.keySet()) {
			// Iterator  지네릭스가 Object 이기 때문에 String 으로 캐스팅한다.
			String command = (String) key;
			// command.properties 에서 나온 value 값을 handlerClassName[]에 저장한다.
			// String 으로 캐스팅을 하여 Object 에서 바꿔주고 내부의 값이 :로 나눠져 있으므로 split(:)을 해준다.
			String[] handlerClassName = ((String) prop.get(command)).split(":");

			try {
				// handlerClass 에 command.properties 에서 0번째로 담은
				// 데이터(com.webjjang.board.service.서비스이름)를 담아준다.
				Class<?> handlerClass = Class.forName(handlerClassName[0]);
				// 자동으로 객체를 생성해서 저장한다.
				// 위에서 담은 데이터를 꺼내어 newInstance()로 평소에 만드는 것처럼
				Action handlerInstance = (Action) handlerClass.newInstance();
				// key : command value <- handlerInstance - key 는 boardDao, command value 는 각
				// service 객체가 된다.
				// serviceBeans Map 에 저장
				serviceBeans.put(command, handlerInstance);
				// 의존성 주입(Dependency Injection) - 사용하는 프로그램을 넣어준다.(setter, 생성자)
				// 생성이 된 service(handlerInstance)에 필요한 Dao 를 가져와서 넣는다.
				// 순서가 반대가 되어도 상관이 없는 이유는 참조형 객체이기 때문.
				handlerInstance.setDao(daoBeans.get(handlerClassName[1]));
				// 인덱스 1번은 "boardDao"이다.
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // end of try-catch;
		} // end of while;
	} // end of init()
} // end of class Beans{}
