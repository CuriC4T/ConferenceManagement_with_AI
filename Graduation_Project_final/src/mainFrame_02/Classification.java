package mainFrame_02;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import javax.crypto.Cipher;

import cryption.CipherFunc;
import databaseClient.Client;


public class Classification extends Vector {
	public Classification() {
		add(new ClassedOrder("회의"));
		add(new ClassedOrder("시간"));
		add(new ClassedOrder("날짜"));
		add(new ClassedOrder("종료"));
		add(new ClassedOrder("관리"));


	}

	public void add(ClassedOrder order) {
		super.add(order);
	}
	void remove(ClassedOrder order) { 
		super.remove(order);
	}
	ClassedOrder getCO(int i) { 
		return (ClassedOrder) elementAt(i);
	}

	public void setClassification(String classification,String order) {
		for (int i = 0; i < size(); i++) {
			if(getCO(i).getClassification().equals(classification)) {
				getCO(i).add(order);
			}
		}
		Client db = new Client();
		try {
			db.getWriter().println(new CipherFunc().encrypt("[SAVE]"+"#"+classification+"#"+order));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	public String getClassification (String order) {
		for (int i = 0; i < size(); i++) {
			if(getCO(i).checkOrder(order)) return getCO(i).getClassification();
		}
		return null;
	}

	public boolean checkClassification(String order) {
		for (int i = 0; i < size(); i++) {
			if(getCO(i).checkOrder(order)) return true;
		}
		return false;
	}
}


