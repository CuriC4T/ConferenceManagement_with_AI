package chatting_WB_Server2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import cryption.CipherFunc;

/**
 * Class that Thread of server
 */
public class ServerThread extends Thread {
	private Random rnd = new Random();

	private int roomNumber = -1; // 방 번호
	private String userName = null; // 사용자 이름
	private Socket socket; // 소켓

	// 준비 여부, true이면 웹캠 시작할 준비가 되었음을 의미한다.

	private boolean ready = false;
	private BufferedReader reader; // 입력 스트림
	private PrintWriter writer; // 출력 스트림
	private BManager Man;

	/**
	 * Generator function of class
	 * 
	 * @param socket socket
	 * @param Man    Manager
	 */
	public ServerThread(Socket socket, BManager Man) { // 생성자

		this.socket = socket;
		this.Man = Man;
	}

	/**
	 * return socket
	 * 
	 * @return socket
	 */
	Socket getSocket() { // 소켓을 반환한다.
		return socket;
	}

	/**
	 * get BoomNumber
	 * 
	 * @return number of Room
	 */
	int getRoomNumber() { // 방 번호를 반환한다.
		return roomNumber;
	}

	/**
	 * get User name
	 * 
	 * @return user name
	 */
	String getUserName() { // 사용자 이름을 반환한다.
		return userName;
	}

	/**
	 * check isReady
	 * 
	 * @return re
	 */
	boolean isReady() { // 준비 상태를 반환한다.
		return ready;
	}

	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);

			String msg; // 클라이언트의 메시지

			while ((msg = reader.readLine()) != null) {
				CipherFunc cipher = new CipherFunc();
				System.out.println(msg);
				msg=cipher.decrypt(msg);
				System.out.println(msg);

				// msg가 "[NAME]"으로 시작되는 메시지이면
				if (msg.startsWith("[NAME]")) {
					userName = msg.substring(6); // userName을 정한다.
				} else if (msg.startsWith("[MSG]"))
					Man.sendToRoom(roomNumber, "[" + userName + "]: " + msg.substring(5));
				else if (msg.startsWith("[WHITE]"))
					Man.sendToRoom(roomNumber, msg);
				// msg가 "[ROOM]"으로 시작되면 방 번호를 정한다.
				else if (msg.startsWith("[ROOM]")) {
					int roomNum = Integer.parseInt(msg.substring(6));
					if (!Man.isFull(roomNum)) { // 방이 찬 상태가 아니면

						// 현재 방의 다른 사용에게 사용자의 퇴장을 알린다.
						if (roomNumber != -1)
							Man.sendToOthers(this, "[EXIT]" + userName);

						// 사용자의 새 방 번호를 지정한다.
						roomNumber = roomNum;

						// 사용자에게 메시지를 그대로 전송하여 입장할 수 있음을 알린다.
						writer.println(msg);

						// 사용자에게 새 방에 있는 사용자 이름 리스트를 전송한다.
						writer.println(Man.getNamesInRoom(roomNumber));

						// 새 방에 있는 다른 사용자에게 사용자의 입장을 알린다.
						Man.sendToOthers(this, "[ENTER]" + userName);
					} else
						writer.println("[FULL]"); // 사용자에 방이 찼음을 알린다.
				}

				else if (msg.startsWith("[START]")) {
//					ready = true;
//					if (Man.isReady(roomNumber)) {
//						Man.sendToRoom(roomNumber, "[WEBCAMON]");
//					}

				} else if (msg.startsWith("[WEBCAM]")) {
//					ready = true;
					
					Man.sendToOthersForIP(this);

				}else if (msg.startsWith("[ALRAM]")) {
					msg=msg.substring(7);
					System.out.println(msg);
					Man.sendToOthersByName(msg,"[ALRAM]"+userName);
					

				} else if (msg.startsWith("[DISCONNECT]")) {
					break;

				}

			}

		} catch (Exception e) {

		} finally {

			try {

				Man.remove(this);

				if (reader != null)
					reader.close();

				if (writer != null)
					writer.close();

				if (socket != null)
					socket.close();

				reader = null;
				writer = null;
				socket = null;

				System.out.println(userName + "님이 접속을 끊었습니다.");

				System.out.println("접속자 수: " + Man.size());

				// 사용자가 접속을 끊었음을 같은 방에 알린다.

				Man.sendToRoom(roomNumber, "[DISCONNECT]" + userName);

			} catch (Exception e) {
			}

		}

	}

}
