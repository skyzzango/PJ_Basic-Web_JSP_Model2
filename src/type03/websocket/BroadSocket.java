package type03.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/broadcasting")
public class BroadSocket {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

	public static void serverMessage(String message) {
		System.out.println("serverMessage 함수 호출");

		for (Session client : clients) {
			try {
				client.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("onMessage 호출");
		System.out.println(message);
		synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message
			for (Session client : clients) {
				if (!client.equals(session)) {
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("onOpen 호출");
		// Add session to the connected sessions set
		System.out.println(session);
		clients.add(session);
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("onClose 호출");
		// Remove session from the connected sessions set
		clients.remove(session);
	}
}
