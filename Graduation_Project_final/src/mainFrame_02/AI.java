package mainFrame_02;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.swing.JLabel;

import chattingwbclient.ChattingClient;

public class AI {
	private JLabel label;
	private DataManagement dataManagement;
	private String ID;
	private TTS tts;
	private boolean flag;
	private Classification classification;
	private ChattingClient chatting;
	private String AISpeek;
	private String temp="";
	
	private MainFrame mainframe ;
	public AI(MainFrame mainframe, JLabel label, TTS tts) {
		this.mainframe = mainframe;
		ID=mainframe.getID();
		this.tts = tts;
		this.label = label;
		dataManagement = new DataManagement();
		dataManagement.loadDataFromFile();
		flag = false;
		classification = new Classification();
	
		AISpeek = "";
	}

	public DataManagement getDataManagement() {
		return dataManagement;
	}

	public void performOrder(String order) {
		System.out.println(order);
		
		if (order.contains("ȸ��")) {
			if (flag) {
				classification.setClassification("ȸ��", temp);
				temp="";
				flag = false;

			}
			meetingOrder();
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
		} else if (order.contains("�ð�")) {
			if (flag) {
				classification.setClassification("�ð�", temp);
				temp="";
				flag = false;

			}
			timeOrder();
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
		} else if (order.contains("��¥")) {
			if (flag) {
				classification.setClassification("��¥", temp);
				temp="";
				flag = false;

			}
			dateOrder();
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
		} else if (order.contains("�˻�")) {
			searchOrder(order);
		} else if (order.contains("����")) {
			if (flag) {
				classification.setClassification("����", temp);
				temp="";
				flag = false;

			}
			
			exitOrder();
			mainframe.getAILabel().setIcon(mainframe.AIimage);
		} else if (order.contains("ȣ��")) {
			try {
				String temp[]=order.split("����");
				mainframe.getWriter().println(mainframe.getCipher().encrypt("[ALRAM]"+temp[0]));
				AISpeek = "ȣ���ϰڽ��ϴ�.";
				label.setText(AISpeek);
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
		} else if (order.contains("����")) {
			if (flag) {
				classification.setClassification("����", temp);
				temp="";
				flag = false;

			}
			planOrder();
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
			label.setText(AISpeek);
		} else if (classification.checkClassification(order)) {
			String addedOrder = classification.getClassification(order);
			classificationOrder(addedOrder);
			System.out.println(addedOrder);
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
			
		} else {
			
			//System.out.println(classification.checkClassification(order));
			boolean isThrereOrder = dataManagement.canDoOrder(order);
			if (!isThrereOrder) {
				AISpeek = "�� �� ���� ��ɾ� �Դϴ�";
				mainframe.getAILabel().setIcon(mainframe.AIimage2);
				label.setText(AISpeek);
				if (!flag) {
					flag = true;
					temp=order;
					System.out.println(temp+" "+flag);
				}

			}else {
				AISpeek = "�˰ڽ��ϴ�";
				label.setText(AISpeek);
			}

		}
		tts.speak(AISpeek);
	}

	public void classificationOrder(String classification) {
		switch (classification) {
		case "ȸ��":
			meetingOrder();
			break;
		case "�ð�":
			timeOrder();
			break;
		case "��¥":
			dateOrder();
			break;
		case "����":
			exitOrder();
			break;
		case "����":
			planOrder();
			break;
		case "�˻�":
			break;
		}
	}

	public void meetingOrder() {
		try {
			chatting = new ChattingClient(mainframe);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatting.setVisible(true);
		AISpeek = "ȸ�� �غ��ϰڽ��ϴ�.";
	}

	public void timeOrder() {
		Calendar cal = Calendar.getInstance();
		AISpeek = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + "�� " + String.valueOf(cal.get(Calendar.MINUTE)) + "�� "
				+ String.valueOf(cal.get(Calendar.SECOND)) + "�� " + "�Դϴ�";
		label.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + (cal.get(Calendar.MINUTE)) + ":" + cal.get(Calendar.SECOND)
				+ "�Դϴ�");
	}

	public void dateOrder() {
		Calendar cal = Calendar.getInstance();
		AISpeek = String.valueOf(cal.get(Calendar.YEAR)) + "�� " + String.valueOf(cal.get(Calendar.MONTH) + 1) + "�� "
				+ String.valueOf(cal.get(Calendar.DATE)) + "�� " + "�Դϴ�";

		label.setText(
				cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "�Դϴ�");

	}

	public void searchOrder(String order) {
		if (new SearchFunc().Searching(order)) {
			AISpeek = "�˻� �Ϸ��߾��";
			label.setText(AISpeek);
		} else {
			AISpeek = "�˻� ���� ����";
			label.setText(AISpeek);
		}
	}

	public void exitOrder() {
		AISpeek = "���� ���� �ſ���? ������ ����";
		tts.speak(AISpeek);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void planOrder() {
		String url = "Index/Static_Full_Version/agile_board.html";
		try {
			File htmlFile = new File(url);
			Desktop.getDesktop().browse(htmlFile.toURI());
			AISpeek = "�����������Դϴ�";
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkFlag() {
		if (flag) {

		}
	}
}
