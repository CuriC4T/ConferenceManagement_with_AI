package databaseClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import cryption.CipherFunc;


public class ClientThread extends Thread {

	private Client client;
	private BufferedReader reader;
	private PrintWriter writer;
	private CipherFunc cipher;

	public ClientThread(Client client) {
		this.client = client;
		try {
			cipher = new CipherFunc();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			String[] table = new String[3];
			String message;
			String[] str;
			reader = client.getReader();
			writer = client.getWriter();
			
			while ((message = reader.readLine()) != null) {
				System.out.println(message);
				message = cipher.decrypt(message);
				System.out.println(message);

				if (message.startsWith("[SAVE]")) {

				} else if (message.startsWith("[DELETE]")) {

				} else if (message.startsWith("[SEARCH]")) {
					str = message.split("#");
					table[0] = str[1];
					
				} else if (message.startsWith("[SEARCHALL]")) {
					System.out.println(message);
					str = message.split("#");
					table[0] = str[1];
					
				}
			}
		} catch (IOException | GeneralSecurityException e) {
		}
	}
}
