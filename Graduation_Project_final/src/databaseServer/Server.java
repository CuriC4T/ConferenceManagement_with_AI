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
			System.out.println("���������� �����Ǿ����ϴ�.");
			while (true) {

				Socket socket = server.accept();
				ServerThread serverThread = new ServerThread(socket,userManagement);
				serverThread.start();
				userManagement.add(serverThread);

				System.out.println("������ ��: " + userManagement.size());
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
