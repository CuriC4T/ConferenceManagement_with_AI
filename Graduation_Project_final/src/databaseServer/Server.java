package databaseServer;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private ServerSocket server;
	private UserManagement userManagement;
	public Server() { 
		userManagement = new UserManagement();
		try {
			server = new ServerSocket(8888);
			System.out.println("서버소켓이 생성되었습니다.");
			while (true) {

				Socket socket = server.accept();
				ServerThread serverThread = new ServerThread(socket,userManagement);
				serverThread.start();
				userManagement.add(serverThread);

				System.out.println("접속자 수: " + userManagement.size());
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
