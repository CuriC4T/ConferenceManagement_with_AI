package databaseServer;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class UserManagement extends Vector {
	
	ServerThread getThread(int i) { // i��° �����带 ��ȯ�Ѵ�.
		return (ServerThread) elementAt(i);
	}
	Socket getSocket(int i) { // i��° �������� ������ ��ȯ�Ѵ�.
		return getThread(i).getSocket();
	}
	void sendToMe(Socket socket, String msg) {
		try {
			for(int i = 0 ;i<size();i++ ) {
				if(getSocket(i)==socket) {
					PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
					pw.println(msg);
				}
			}
			
		} catch (Exception e) {
		}
	}

}
