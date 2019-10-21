package databaseServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import cryption.CipherFunc;
import database.ConnectDB;
import database.DTO;

public class ServerThread extends Thread {
	private ConnectDB DB;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private UserManagement userManagement;
	CipherFunc cipher;
	public ServerThread(Socket socket, UserManagement userManagement) {
		try {
			cipher=new CipherFunc();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.socket = socket;
		this.userManagement = userManagement;
		DB = new ConnectDB();
	}

	public void run() {
		try {
			
			String message;
			String[] str;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			while ((message = reader.readLine()) != null) {
				System.out.println(message);
				System.out.println(message.length());

				message = cipher.decrypt(message);
				System.out.println(message);

				if (message.startsWith("[SAVE]")) {
					str = message.split("#");
					
					DB.insertDTO(str[1], str[2]);
					userManagement.sendToMe(socket, cipher.encrypt("저장"));

				} 
				else if (message.startsWith("[DELETE]")) {
					str = message.split("#");
					DB.deleteDTO(Integer.valueOf(str[1]));
					userManagement.sendToMe(socket, cipher.encrypt("삭제"));
				}
				else if (message.startsWith("[SEARCH]")) {
					str = message.split("#");
					DTO dto=DB.selectOne(str[1], str[2]);
					if(dto!=null&&dto.getClassification()!=null) {
						userManagement.sendToMe(socket, cipher.encrypt("[SEARCH]"+"#"+dto.getID()+"#"+dto.getClassification()+"#"+dto.getOrder()));
						userManagement.sendToMe(socket, cipher.encrypt("검색"));
					}
					
				}else if (message.startsWith("[SEARCHALL]")) {
					List<DTO> list =DB.selectAll();
					Iterator itr = list.iterator();
					while(itr.hasNext()) {
						DTO dto = (DTO) itr.next();
						userManagement.sendToMe(socket, cipher.encrypt("[SEARCHALL]"+"#"+dto.getID()+"#"+dto.getClassification()+"#"+dto.getOrder()));
					}
					userManagement.sendToMe(socket, cipher.encrypt("모든 검색"));
				}
			}

		} catch (IOException | GeneralSecurityException e) {
			
		} finally {

			try {

				userManagement.remove(this);

				if (reader != null)
					reader.close();

				if (writer != null)
					writer.close();

				if (socket != null)
					socket.close();

				reader = null;
				writer = null;
				socket = null;
				DB.endDB();
				System.out.println("사용자님 이 접속을 끊었습니다.");

				System.out.println("접속자 수: " + userManagement.size());

			} catch (Exception e) {

			}
		}

	}

	Socket getSocket() { // 소켓을 반환한다.
		return socket;
	}

}
