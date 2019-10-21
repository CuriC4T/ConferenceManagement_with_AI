package chatting_WB_Server2;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/**
 * class for  send  message
 *
 */
class Manager extends Vector { // 메시지를 전달하는 클래스
	Manager() {
	}

	/**
	 * add client user to vector
	 * @param ot ServerThread
	 */
	void add(ServerThread ot) { // 스레드를 추가한다.
		super.add(ot);
	}

	/** 
	 * remove client uset from vector
	 * @param ot ServerThread
	 */
	void remove(ServerThread ot) { // 스레드를 제거한다.
		super.remove(ot);
	}

	/**
	 * Get Thread index i
	 * @param i index
	 * @return ServerThread
	 */
	ServerThread getOT(int i) { // i번째 스레드를 반환한다.
		return (ServerThread) elementAt(i);
	}

	/**
	 * Get socket index i
	 * @param i
	 * @return
	 */
	Socket getSocket(int i) { // i번째 스레드의 소켓을 반환한다.
		return getOT(i).getSocket();
	}

	// i번째 스레드와 연결된 클라이언트에게 메시지를 전송한다.

	/**
	 * send message to i-th user
	 * @param i index
	 * @param msg message
	 */
	void sendTo(int i, String msg) {
		try {
			PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
			pw.println(msg);
		} catch (Exception e) {
		}
	}

	/**
	 * return i-th RoomNumber
	 * @param i index
	 * @return room number
	 */
	int getRoomNumber(int i) { // i번째 스레드의 방 번호를 반환한다.
		return getOT(i).getRoomNumber();
	}

	/**
	 * Check whether Room is full
	 * @param roomNum number of Room
	 * @return true if full
	 */
	synchronized boolean isFull(int roomNum) { // 방이 찼는지 알아본다.
		if (roomNum == 0)
			return false; // 대기실은 차지 않는다.
		// 다른 방은 2명 이상 입장할 수 없다.
		int count = 0;
		for (int i = 0; i < size(); i++)
			if (roomNum == getRoomNumber(i))
				count++;
		if (count >= 2)
			return true;
		return false;
	}

	// roomNum 방에 msg를 전송한다.

	/**
	 * send Message Message to room
	 * @param roomNum number of Room
	 * @param msg message
	 */
	void sendToRoom(int roomNum, String msg) {
		for (int i = 0; i < size(); i++)
			if (roomNum == getRoomNumber(i))
				sendTo(i, msg);
	}

	// ot와 같은 방에 있는 다른 사용자에게 msg를 전달한다.

	/**
	 * send Message to  other player
	 * @param ot ServerThread
	 * @param msg message
	 */
	void sendToOthers(ServerThread ot, String msg) {
		for (int i = 0; i < size(); i++)
			if (getRoomNumber(i) == ot.getRoomNumber() && getOT(i) != ot)
				sendTo(i, msg);
		
	}
	void sendToOthersByName(String name, String msg) {
		for (int i = 0; i < size(); i++) {
			System.out.println(getOT(i).getUserName());

			if (getOT(i).getUserName().equals(name)) {
				System.out.println("메세지 보냄");
				sendTo(i, msg);
			}
		}
			
			
				
		
	}
	void sendToOthersForIP(ServerThread ot) {
		for (int i = 0; i < size(); i++)
			if (getRoomNumber(i) == ot.getRoomNumber() && getOT(i) != ot) {
				try {
					PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
					String ip=String.valueOf(getSocket(i).getInetAddress());
					pw.println("[WEBCAM]"+ip.substring(1)+"#"+getSocket(i).getPort());
				} catch (Exception e) {
				}
			}
	}

	

	/**
	 * check players are ready to webcam
	 * @param roomNum number of room
	 * @return true if ready
	 */
	synchronized boolean isReady(int roomNum) {
		int count = 0;
		for (int i = 0; i < size(); i++)
			if (roomNum == getRoomNumber(i) && getOT(i).isReady())
				count++;
		if (count == 2)
			return true;
		return false;
	}

	// roomNum방에 있는 사용자들의 이름을 반환한다.

	/**
	 * get name of players
	 * @param roomNum number of room
	 * @return names of players
	 */
	String getNamesInRoom(int roomNum) {
		StringBuffer sb = new StringBuffer("[PLAYERS]");
		for (int i = 0; i < size(); i++)
			if (roomNum == getRoomNumber(i))
				sb.append(getOT(i).getUserName() + "\t");
		return sb.toString();
	}

}
