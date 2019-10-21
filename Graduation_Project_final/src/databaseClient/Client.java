package databaseClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.table.DefaultTableModel;

import mainFrame_02.Classification;

public class Client {
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	
	public Client() {
		
		init();
	}
	
	public void init() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println(inetAddress);
			socket = new Socket(inetAddress, 8888);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			ClientThread clientThread =new ClientThread(this);
			clientThread.start();
			
		} catch (Exception e) {
			
		}
	}

	public BufferedReader getReader() {
		return reader;
	}
	
	public PrintWriter getWriter() {
		return writer;
	}
	
	

}
