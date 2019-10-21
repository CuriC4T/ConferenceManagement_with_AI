package chatting_WB_Server2;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/**
 * class for  send  message
 *
 */
class Manager extends Vector { // �޽����� �����ϴ� Ŭ����
	Manager() {
	}

	/**
	 * add client user to vector
	 * @param ot ServerThread
	 */
	void add(ServerThread ot) { // �����带 �߰��Ѵ�.
		super.add(ot);
	}

	/** 
	 * remove client uset from vector
	 * @param ot ServerThread
	 */
	void remove(ServerThread ot) { // �����带 �����Ѵ�.
		super.remove(ot);
	}

	/**
	 * Get Thread index i
	 * @param i index
	 * @return ServerThread
	 */
	ServerThread getOT(int i) { // i��° �����带 ��ȯ�Ѵ�.
		return (ServerThread) elementAt(i);
	}

	/**
	 * Get socket index i
	 * @param i
	 * @return
	 */
	Socket getSocket(int i) { // i��° �������� ������ ��ȯ�Ѵ�.
		return getOT(i).getSocket();
	}

	// i��° ������� ����� Ŭ���̾�Ʈ���� �޽����� �����Ѵ�.

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
	int getRoomNumber(int i) { // i��° �������� �� ��ȣ�� ��ȯ�Ѵ�.
		return getOT(i).getRoomNumber();
	}

	/**
	 * Check whether Room is full
	 * @param roomNum number of Room
	 * @return true if full
	 */
	synchronized boolean isFull(int roomNum) { // ���� á���� �˾ƺ���.
		if (roomNum == 0)
			return false; // ������ ���� �ʴ´�.
		// �ٸ� ���� 2�� �̻� ������ �� ����.
		int count = 0;
		for (int i = 0; i < size(); i++)
			if (roomNum == getRoomNumber(i))
				count++;
		if (count >= 2)
			return true;
		return false;
	}

	// roomNum �濡 msg�� �����Ѵ�.

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

	// ot�� ���� �濡 �ִ� �ٸ� ����ڿ��� msg�� �����Ѵ�.

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
				System.out.println("�޼��� ����");
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

	// roomNum�濡 �ִ� ����ڵ��� �̸��� ��ȯ�Ѵ�.

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
