package databaseServer;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class UserManagement extends Vector {
	
	ServerThread getThread(int i) { // i번째 스레드를 반환한다.
		return (ServerThread) elementAt(i);
	}
	Socket getSocket(int i) { // i번째 스레드의 소켓을 반환한다.
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
