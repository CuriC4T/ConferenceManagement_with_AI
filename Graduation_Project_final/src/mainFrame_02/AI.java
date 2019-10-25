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
		
		if (order.contains("회의")) {
			if (flag) {
				classification.setClassification("회의", temp);
				temp="";
				flag = false;

			}
			meetingOrder();
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
		} else if (order.contains("시간")) {
			if (flag) {
				classification.setClassification("시간", temp);
				temp="";
				flag = false;

			}
			timeOrder();
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
		} else if (order.contains("날짜")) {
			if (flag) {
				classification.setClassification("날짜", temp);
				temp="";
				flag = false;

			}
			dateOrder();
			mainframe.getAILabel().setIcon(mainframe.AI_Thinking);
		} else if (order.contains("검색")) {
			searchOrder(order);
		} else if (order.contains("종료")) {
			if (flag) {
				classification.setClassification("종료", temp);
				temp="";
				flag = false;

			}
			
			exitOrder();
			mainframe.getAILabel().setIcon(mainframe.AIimage);
		} else if (order.contains("호출")) {
			try {
				String temp[]=order.split("에게");
				mainframe.getWriter().println(mainframe.getCipher().encrypt("[ALRAM]"+temp[0]));
				AISpeek = "호출하겠습니다.";
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
		} else if (order.contains("관리")) {
			if (flag) {
				classification.setClassification("관리", temp);
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
				AISpeek = "알 수 없는 명령어 입니다";
				mainframe.getAILabel().setIcon(mainframe.AIimage2);
				label.setText(AISpeek);
				if (!flag) {
					flag = true;
					temp=order;
					System.out.println(temp+" "+flag);
				}

			}else {
				AISpeek = "알겠습니다";
				label.setText(AISpeek);
			}

		}
		tts.speak(AISpeek);
	}

	public void classificationOrder(String classification) {
		switch (classification) {
		case "회의":
			meetingOrder();
			break;
		case "시간":
			timeOrder();
			break;
		case "날짜":
			dateOrder();
			break;
		case "종료":
			exitOrder();
			break;
		case "관리":
			planOrder();
			break;
		case "검색":
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
		AISpeek = "회의 준비하겠습니다.";
	}

	public void timeOrder() {
		Calendar cal = Calendar.getInstance();
		AISpeek = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + "시 " + String.valueOf(cal.get(Calendar.MINUTE)) + "분 "
				+ String.valueOf(cal.get(Calendar.SECOND)) + "초 " + "입니다";
		label.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + (cal.get(Calendar.MINUTE)) + ":" + cal.get(Calendar.SECOND)
				+ "입니다");
	}

	public void dateOrder() {
		Calendar cal = Calendar.getInstance();
		AISpeek = String.valueOf(cal.get(Calendar.YEAR)) + "년 " + String.valueOf(cal.get(Calendar.MONTH) + 1) + "월 "
				+ String.valueOf(cal.get(Calendar.DATE)) + "일 " + "입니다";

		label.setText(
				cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "입니다");

	}

	public void searchOrder(String order) {
		if (new SearchFunc().Searching(order)) {
			AISpeek = "검색 완료했어요";
			label.setText(AISpeek);
		} else {
			AISpeek = "검색 엔진 오류";
			label.setText(AISpeek);
		}
	}

	public void exitOrder() {
		AISpeek = "벌써 가는 거에요? 다음에 봐요";
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
			AISpeek = "관리페이지입니다";
			
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
