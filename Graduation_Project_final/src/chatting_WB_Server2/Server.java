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
	public Server() { // ������ �����Ѵ�.
		try {
			server = new ServerSocket(7777);
			System.out.println("���������� �����Ǿ����ϴ�.");
			while (true) {

				// Ŭ���̾�Ʈ�� ����� �����带 ��´�.
				Socket socket = server.accept();
				// �����带 ����� �����Ų��.
				ServerThread ot = new ServerThread(socket, bMan);
				ot.start();

				// bMan�� �����带 �߰��Ѵ�.
				bMan.add(ot);

				System.out.println("������ ��: " + bMan.size());
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
