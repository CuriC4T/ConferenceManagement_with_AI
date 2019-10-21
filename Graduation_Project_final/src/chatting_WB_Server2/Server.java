package chatting_WB_Server2;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Game Server
 */
public class Server {
	private ServerSocket server;
	private Manager bMan = new Manager();

	/**
	 * Gneerator of class 
	 */
	public Server() { // 서버를 실행한다.
		try {
			server = new ServerSocket(7777);
			System.out.println("서버소켓이 생성되었습니다.");
			while (true) {

				// 클라이언트와 연결된 스레드를 얻는다.
				Socket socket = server.accept();
				// 스레드를 만들고 실행시킨다.
				ServerThread ot = new ServerThread(socket, bMan);
				ot.start();

				// bMan에 스레드를 추가한다.
				bMan.add(ot);

				System.out.println("접속자 수: " + bMan.size());
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
