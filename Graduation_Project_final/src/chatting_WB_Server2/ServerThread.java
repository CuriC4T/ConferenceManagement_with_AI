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

	private int roomNumber = -1; // �� ��ȣ
	private String userName = null; // ����� �̸�
	private Socket socket; // ����

	// �غ� ����, true�̸� ��ķ ������ �غ� �Ǿ����� �ǹ��Ѵ�.

	private boolean ready = false;
	private BufferedReader reader; // �Է� ��Ʈ��
	private PrintWriter writer; // ��� ��Ʈ��
	private BManager Man;

	/**
	 * Generator function of class
	 * 
	 * @param socket socket
	 * @param Man    Manager
	 */
	public ServerThread(Socket socket, BManager Man) { // ������

		this.socket = socket;
		this.Man = Man;
	}

	/**
	 * return socket
	 * 
	 * @return socket
	 */
	Socket getSocket() { // ������ ��ȯ�Ѵ�.
		return socket;
	}

	/**
	 * get BoomNumber
	 * 
	 * @return number of Room
	 */
	int getRoomNumber() { // �� ��ȣ�� ��ȯ�Ѵ�.
		return roomNumber;
	}

	/**
	 * get User name
	 * 
	 * @return user name
	 */
	String getUserName() { // ����� �̸��� ��ȯ�Ѵ�.
		return userName;
	}

	/**
	 * check isReady
	 * 
	 * @return re
	 */
	boolean isReady() { // �غ� ���¸� ��ȯ�Ѵ�.
		return ready;
	}

	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);

			String msg; // Ŭ���̾�Ʈ�� �޽���

			while ((msg = reader.readLine()) != null) {
				CipherFunc cipher = new CipherFunc();
				System.out.println(msg);
				msg=cipher.decrypt(msg);
				System.out.println(msg);

				// msg�� "[NAME]"���� ���۵Ǵ� �޽����̸�
				if (msg.startsWith("[NAME]")) {
					userName = msg.substring(6); // userName�� ���Ѵ�.
				} else if (msg.startsWith("[MSG]"))
					Man.sendToRoom(roomNumber, "[" + userName + "]: " + msg.substring(5));
				else if (msg.startsWith("[WHITE]"))
					Man.sendToRoom(roomNumber, msg);
				// msg�� "[ROOM]"���� ���۵Ǹ� �� ��ȣ�� ���Ѵ�.
				else if (msg.startsWith("[ROOM]")) {
					int roomNum = Integer.parseInt(msg.substring(6));
					if (!Man.isFull(roomNum)) { // ���� �� ���°� �ƴϸ�

						// ���� ���� �ٸ� ��뿡�� ������� ������ �˸���.
						if (roomNumber != -1)
							Man.sendToOthers(this, "[EXIT]" + userName);

						// ������� �� �� ��ȣ�� �����Ѵ�.
						roomNumber = roomNum;

						// ����ڿ��� �޽����� �״�� �����Ͽ� ������ �� ������ �˸���.
						writer.println(msg);

						// ����ڿ��� �� �濡 �ִ� ����� �̸� ����Ʈ�� �����Ѵ�.
						writer.println(Man.getNamesInRoom(roomNumber));

						// �� �濡 �ִ� �ٸ� ����ڿ��� ������� ������ �˸���.
						Man.sendToOthers(this, "[ENTER]" + userName);
					} else
						writer.println("[FULL]"); // ����ڿ� ���� á���� �˸���.
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

				System.out.println(userName + "���� ������ �������ϴ�.");

				System.out.println("������ ��: " + Man.size());

				// ����ڰ� ������ �������� ���� �濡 �˸���.

				Man.sendToRoom(roomNumber, "[DISCONNECT]" + userName);

			} catch (Exception e) {
			}

		}

	}

}
