package chattingwbclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cryption.CipherFunc;
import mainFrame_02.Main;
import mainFrame_02.MainFrame;
import webcamClient.WebCamClient;
import webcamServer.WebCamServer;

/**
 * Client page setting class
 */
public class ChattingClient extends Frame implements Runnable, ActionListener {
	private int X, Y;
	private CipherFunc cipher;
	private ImageIcon eraseIcon;
	private ImageIcon allclearIcon;
	private ImageIcon changeColorButtonIcon;
	private ImageIcon largerPenIcon;
	private ImageIcon smallerPenIcon;

	private ImageIcon eraseClikedIcon;
	private ImageIcon allclearClikedIcon;
	private ImageIcon changeColorButtonClikedIcon;
	private ImageIcon largerPenClikedIcon;
	private ImageIcon smallerPenClikedIcon;
	private ImageIcon exitIcon;
	private ImageIcon exitIcon_cliked;
	private JPanel toolPanel;

	private JButton exit;
	private JButton erase;
	private JButton allclear;
	private JButton changeColorButton;
	private JButton largerPen;
	private JButton smallerPen;

	private Color myColor;

	private JTextArea msgView = new JTextArea("", 1, 1); // 메시지를 보여주는 영역
	private JTextField sendBox = new JTextField(""); // 보낼 메시지를 적는 상자
	private JTextField nameBox = new JTextField(); // 사용자 이름 상자
	private JTextField roomBox = new JTextField("0"); // 방 번호 상자

	// 방에 접속한 인원의 수를 보여주는 레이블
	private JLabel pInfo = new JLabel("대기실:  명");

	private java.awt.List pList = new java.awt.List(); // 사용자 명단을 보여주는 리스트
	private JButton startButton = new JButton("웹캠 켜기"); // 웹캠 시작 버튼
	private JButton WebcamButton = new JButton("웹캠 접속"); // 웹캠 접속 버튼
	private JButton stopButton = new JButton("웹캠 끄기"); // 웹캠 종료 버튼
	private JButton enterButton = new JButton("입장하기"); // 입장하기 버튼
	private JButton exitButton = new JButton("대기실로"); // 대기실로 버튼

	// 각종 정보를 보여주는 레이블
	private JLabel infoView = new JLabel("안녕하세요");

	private BufferedReader reader; // 입력 스트림
	private PrintWriter writer; // 출력 스트림
	private Socket socket; // 소켓
	private int roomNumber = -1; // 방 번호
	private String userName = null; // 사용자 이름
	private MyCanvas can;
	private MainFrame mainframe;
	/**
	 * Generator function of class
	 * 
	 * @param userName name of user
	 * @throws UnsupportedEncodingException
	 */
	public ChattingClient(MainFrame mainframe) throws UnsupportedEncodingException { // 생성자
		cipher = mainframe.getCipher();
		this.mainframe = mainframe;
		userName=mainframe.getID();
		socket=mainframe.getSocket();
		reader=mainframe.getReader();
		writer=mainframe.getWriter();
		//connect();
		new Thread(this).start();
		nameBox.setText(userName);
		nameBox.setEditable(false);
		setResizable(false); // size 변경 불가
		setLocationRelativeTo(null);
		setSize(785, 625);
		setLayout(null); // 레이아웃을 사용하지 않는다.
		setBackground(new Color(50, 50, 50));

		// 각종 컴포넌트를 생성하고 배치한다.
		can = new MyCanvas();
		can.setLocation(10, 70);
		init();
		settingComponent();
		msgView.setEditable(false);
		infoView.setBounds(10, 30, 480, 30);
		infoView.setBackground(new Color(51, 102, 255));
		infoView.setForeground(new Color(51, 102, 255));

		add(infoView);

		JPanel p = new JPanel();
		p.setBackground(new Color(102, 255, 153));
		p.setLayout(new GridLayout(3, 3));
		p.add(new JLabel("이     름:", 2));
		p.add(nameBox);
		p.add(new JLabel("방 번호:", 2));
		p.add(roomBox);
		p.add(enterButton);
		p.add(exitButton);
		enterButton.setEnabled(false);
		p.setBounds(525, 30, 250, 70);

		JPanel p2 = new JPanel();
		p2.setBackground(new Color(255, 153, 153));
		p2.setLayout(new BorderLayout());
		JPanel p2_1 = new JPanel();
		p2_1.add(startButton);
		p2_1.add(WebcamButton);
		p2_1.add(stopButton);
		p2.add(pInfo, "North");
		p2.add(pList, "Center");
		p2.add(p2_1, "South");
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		p2.setBounds(525, 110, 250, 180);

		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.add(msgView, "Center");
		p3.add(sendBox, "South");
		p3.setBounds(525, 300, 250, 250);

		add(p);
		add(p2);
		add(p3);
		add(can);
		add(toolPanel);

		// 이벤트 리스너를 등록한다.
		sendBox.addActionListener(this);
		enterButton.addActionListener(this);
		exitButton.addActionListener(this);
		startButton.addActionListener(this);
		WebcamButton.addActionListener(this);

		stopButton.addActionListener(this);

		// 윈도우 닫기 처리
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

	}

	public void init() {
		exitIcon = new ImageIcon(Main.class.getResource("../Images/canvasExitbutton.png"));
		exitIcon_cliked = new ImageIcon(Main.class.getResource("../Images/canvasExitbutton_checked.png"));
		eraseIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonEraser.png"));
		allclearIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonEraserAll.png"));
		changeColorButtonIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonChange.png"));
		largerPenIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonPlus.png"));
		smallerPenIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonMinus.png"));

		eraseClikedIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonEraserCliked.png"));
		allclearClikedIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonEraserAllCliked.png"));
		changeColorButtonClikedIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonChange.png"));
		largerPenClikedIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonPlusCliked.png"));
		smallerPenClikedIcon = new ImageIcon(Main.class.getResource("../Images/whiteboardButtonMinuscliked.png"));

		exit = new JButton(exitIcon);
		erase = new JButton(eraseIcon);
		allclear = new JButton(allclearIcon);
		changeColorButton = new JButton(changeColorButtonIcon);
		largerPen = new JButton(largerPenIcon);
		smallerPen = new JButton(smallerPenIcon);
		myColor = Color.BLACK;
		toolPanel = new JPanel();

		toolPanel.setBounds(10, 500, 408, 120);
		toolPanel.setLayout(new GridLayout(1, 6));
		toolPanel.setBackground(new Color(54, 54, 54));
		exit.setSize(68, 80);
		erase.setSize(68, 80);// 지우개
		allclear.setSize(68, 80);// 다 지우기
		changeColorButton.setSize(68, 80);// 색깔 바꾸기
		largerPen.setSize(68, 80);// 펜 크게
		smallerPen.setSize(68, 80);// 펜 작게

		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		erase.setBorderPainted(false);
		erase.setContentAreaFilled(false);
		erase.setFocusPainted(false);
		////////////////// 버튼 투명하게,테두리 등등 지우기
		allclear.setBorderPainted(false);
		allclear.setContentAreaFilled(false);
		allclear.setFocusPainted(false);
		changeColorButton.setBorderPainted(false);
		changeColorButton.setContentAreaFilled(false);
		changeColorButton.setFocusPainted(false);
		largerPen.setBorderPainted(false);
		largerPen.setContentAreaFilled(false);
		largerPen.setFocusPainted(false);
		smallerPen.setBorderPainted(false);
		smallerPen.setContentAreaFilled(false);
		smallerPen.setFocusPainted(false);

		toolPanel.add(erase);
		toolPanel.add(allclear);
		toolPanel.add(changeColorButton);
		toolPanel.add(largerPen);
		toolPanel.add(smallerPen);
		toolPanel.add(exit);

	}

	public void settingComponent() {
		/***********************
		 * 컴포넌트들 이벤트 처리
		 *********************/
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				exit.setIcon(exitIcon_cliked);
				exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {

				exit.setIcon(exitIcon);
				exit.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		erase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				erase.setIcon(eraseClikedIcon);
				erase.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				erase.setIcon(eraseIcon);
				erase.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		allclear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				allclear.setIcon(allclearClikedIcon);
				allclear.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				allclear.setIcon(allclearIcon);
				allclear.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		changeColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				changeColorButton.setIcon(changeColorButtonClikedIcon);
				changeColorButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {

				changeColorButton.setIcon(changeColorButtonIcon);
				changeColorButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});

		largerPen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				largerPen.setIcon(largerPenClikedIcon);
				largerPen.setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				largerPen.setIcon(largerPenIcon);
				largerPen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		smallerPen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				smallerPen.setIcon(smallerPenClikedIcon);
				smallerPen.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				smallerPen.setIcon(smallerPenIcon);
				smallerPen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		/*
		 * 캔버스 좌표 값 처리
		 */
		can.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				// 마우스를 드래그한 지점의 x좌표,y좌표를 얻어와서 can의 x,y 좌표값에 전달한다.
				X = e.getX();
				Y = e.getY();

				try {
					writer.println(cipher.encrypt("[WHITE]" + "#" + X + "#" + Y + "#" + myColor.getRGB() + "#"
							+ can.getW() + "#" + can.getH()));
					System.out.println( myColor.getRGB());
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GeneralSecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				can.repaint();

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}

		});
		// 버튼들 이벤트 처리
		largerPen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyCanvas can2 = (MyCanvas) can;
				can2.w += 10;
				can2.h += 10;
				can2.setX(-50);
				can2.setY(-50);
			}

		});
		smallerPen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				MyCanvas can2 = (MyCanvas) can;
				if (can.w > 10 && can.h > 10) {
					can2.w -= 10;
					can2.setX(-50);
					can2.setY(-50);
					can2.h -= 10;
				}

			}

		});

		erase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WebCamServer();
				// TODO Auto-generated method stub
				MyCanvas can2 = (MyCanvas) can;
				can2.setCr(can.getBackground());
				myColor = Color.white;
			}

		});
		allclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					writer.println(
							cipher.encrypt("[WHITE]" + "#" + "allclear" + "#" + 0 + "#" + 0 + "#" + 0 + "#" + 0));
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GeneralSecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		changeColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyCanvas can2 = (MyCanvas) can;
				myColor = JColorChooser.showDialog(null, "색선정", Color.blue);
				can2.cr = myColor;
				can2.x = -50;
				can2.y = -50;
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					writer.println(cipher.encrypt("[DISCONNECT]" + userName));
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GeneralSecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
				dispose();

			}

		});
	}

	// 컴포넌트들의 액션 이벤트 처리
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == sendBox) { // 메시지 입력 상자이면
			String msg = sendBox.getText();
			//System.out.println(msg);
			if (msg.length() == 0)
				return;
			if (msg.length() >= 30)
				msg = msg.substring(0, 30);
			try {
				writer.println(cipher.encrypt("[MSG]" + msg));
				sendBox.setText("");
			} catch (Exception ie) {
			}
		}

		else if (ae.getSource() == enterButton) { // 입장하기 버튼이면
			try {
				if (Integer.parseInt(roomBox.getText()) < 1) {
					infoView.setText("방번호가 잘못되었습니다. 1이상");
					JOptionPane.showMessageDialog(null, "방번호가 잘못되었습니다. 1이상", "방번호", JOptionPane.WARNING_MESSAGE);
					return;
				}
				MyCanvas can2 = (MyCanvas) can;
				Graphics g = can2.getGraphics();
				g.clearRect(0, 0, can.getWidth(), can.getHeight());
				writer.println(cipher.encrypt("[ROOM]" + Integer.parseInt(roomBox.getText())));
				msgView.setText("");
			} catch (Exception ie) {
				infoView.setText("입력하신 사항에 오류가 았습니다.");
				JOptionPane.showMessageDialog(null, "입력하신 사항에 오류가 았습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (ae.getSource() == exitButton) { // 대기실로 버튼이면
			try {
				goToWaitRoom();
				startButton.setEnabled(true);
				stopButton.setEnabled(true);
				MyCanvas can2 = (MyCanvas) can;
				Graphics g = can2.getGraphics();
				g.clearRect(0, 0, can.getWidth(), can.getHeight());

			} catch (Exception e) {
			}
		}

		else if (ae.getSource() == startButton) {
			try {
				// writer.println("[START]");
				infoView.setText("상대의 결정을 기다립니다.");
				startButton.setEnabled(false);
				new Frame("asd").setVisible(true);
				// WebCamServer aa =new WebCamServer();
			} catch (Exception e) {
			}
		} else if (ae.getSource() == WebcamButton) {
			try {
				// writer.println("[START]");
				infoView.setText("상대의 결정을 기다립니다.");
				WebcamButton.setEnabled(false);
				WebCamClient webcamclient = new WebCamClient("172.30.1.50", 6782);
			} catch (Exception e) {
			}
		}

		else if (ae.getSource() == stopButton) {
			try {
				startButton.setEnabled(true);
				WebcamButton.setEnabled(true);

			} catch (Exception e) {
			}
		}
	}

	void goToWaitRoom() { // 대기실로 버튼을 누르면 호출된다.
		try {
			writer.println(cipher.encrypt("[NAME]" + userName));
			msgView.setText("");
			writer.println(cipher.encrypt("[ROOM]0"));
			infoView.setText("대기실에 입장하셨습니다.");
			// JOptionPane.showMessageDialog(null, "대기실에 입장하셨습니다.");
			roomBox.setText("0");
			enterButton.setEnabled(true);
			exitButton.setEnabled(false);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		String msg; // 서버로부터의 메시지
		try {
			while ((msg = reader.readLine()) != null) {
				//System.out.println(msg);
				if (msg.startsWith("[ROOM]")) { // 방에 입장
					if (!msg.equals("[ROOM]0")) { // 대기실이 아닌 방이면
						enterButton.setEnabled(false);
						exitButton.setEnabled(true);
						infoView.setText(msg.substring(6) + "번 방에 입장하셨습니다.");
						JOptionPane.showMessageDialog(null, msg.substring(6) + "번 방에 입장하셨습니다.");
					} else {
						infoView.setText("대기실에 입장하셨습니다.");
						JOptionPane.showMessageDialog(null, "대기실에 입장하셨습니다.");
					}

					roomNumber = Integer.parseInt(msg.substring(6)); // 방 번호 지정

				}

				else if (msg.startsWith("[PLAYERS]")) { // 방에 있는 사용자 명단
					nameList(msg.substring(9));
				}

				else if (msg.startsWith("[ENTER]")) { // 손님 입장
					pList.add(msg.substring(7));
					playersInfo();
					msgView.append("[" + msg.substring(7) + "]님이 입장하였습니다.\n");
				}
				else if (msg.startsWith("[ALRAM]")) { 
					msg=msg.substring(7);
					JOptionPane.showMessageDialog(null, msg+"님의 호출입니다.");
					
				}

				else if (msg.startsWith("[EXIT]")) { // 손님 퇴장
					pList.remove(msg.substring(6)); // 리스트에서 제거
					playersInfo(); // 인원수를 다시 계산하여 보여준다.
					msgView.append("[" + msg.substring(6) + "]님이 다른 방으로 입장하였습니다.\n");
					if (roomNumber != 0) {
						endWebcam("상대가 나갔습니다.");
						JOptionPane.showMessageDialog(null, "상대가 나갔습니다.");
					}
				}

				else if (msg.startsWith("[DISCONNECT]")) { // 손님 접속 종료
					pList.remove(msg.substring(12));
					playersInfo();
					msgView.append("[" + msg.substring(12) + "]님이 접속을 끊었습니다.\n");
					if (roomNumber != 0) {
						endWebcam("상대가 나갔습니다.");
						JOptionPane.showMessageDialog(null, "상대가 나갔습니다.");
					}
				} else if (msg.startsWith("[WHITE]")) {

					String[] receivedMsg = msg.split("#");

					Thread.sleep(1);
					if (receivedMsg[1].equals("allclear")) {
						MyCanvas can2 = (MyCanvas) can;
						Graphics g = can2.getGraphics();
						g.clearRect(0, 0, can.getWidth(), can.getHeight());
					} else {
						can.setX(Integer.valueOf(receivedMsg[1]));
						can.setY(Integer.valueOf(receivedMsg[2]));
						can.setCr(new Color(Integer.valueOf(receivedMsg[3])));
						can.setW(Integer.valueOf(receivedMsg[4]));
						can.setH(Integer.valueOf(receivedMsg[5]));
						can.repaint();
						

					}

				} else if (msg.startsWith("[WEBCAMON]")) {
					// new WebCamServer();
					// new WebCamClient("172.30.1.50",6782);

					//System.out.println(msg);
					writer.println(cipher.encrypt("[WEBCAM]"));
				} else if (msg.startsWith("[WEBCAM]")) {
					//System.out.println(msg);
					String subMsg = msg.substring(8);
				//	//System.out.println(subMsg);
					String[] IpPort = subMsg.split("#");
					//System.out.println(IpPort);
					//System.out.println(IpPort[0]);
					//System.out.println(Integer.parseInt(IpPort[1]));

				} else if (msg.startsWith("[DISCONNECT]")) {
					pList.remove(msg.substring(12));
					playersInfo();
					msgView.append("[" + msg.substring(12) + "]님이 접속을 끊었습니다.\n");
					if (roomNumber != 0) {
						JOptionPane.showMessageDialog(null, "상대가 나갔습니다.");
					}

				}

				// 약속된 메시지가 아니면 메시지 영역에 보여준다.
				else
					msgView.append(msg + "\n");
			}
		} catch (IOException ie) {
			msgView.append(ie + "\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msgView.append("접속이 끊겼습니다.");
	}

	/**
	 * ending webcam function
	 * 
	 * @param msg message that end webcam
	 */
	private void endWebcam(String msg) { //
		infoView.setText(msg);
		startButton.setEnabled(false);
		stopButton.setEnabled(false);

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		} // 2초간 대기

		// if (board.isRunning())
		// board.webcam();
		// if (pList.getItemCount() == 2)
		// startButton.setEnabled(true);
	}

	/**
	 * Show number of Player
	 */
	private void playersInfo() { // 방에 있는 접속자의 수를 보여준다.
		int count = pList.getItemCount();
		if (roomNumber == 0)
			pInfo.setText("대기실: " + count + "명");
		else
			pInfo.setText(roomNumber + " 번 방: " + count + "명");

		// if (count == 2 && roomNumber != 0)
		// startButton.setEnabled(true);
		// else
		// startButton.setEnabled(false);
	}

	// 사용자 리스트에서 사용자들을 추출하여 pList에 추가한다.
	/**
	 * Name List of player
	 * 
	 * @param msg String of Player that will be splite with token
	 */
	private void nameList(String msg) {
		pList.removeAll();
		StringTokenizer st = new StringTokenizer(msg, "\t");
		while (st.hasMoreElements())
			pList.add(st.nextToken());
		playersInfo();
	}

	/**
	 * Connect Server
	
	void connect() { // 연결
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			msgView.append("서버에 연결을 요청합니다.\n");
			System.out.println(inetAddress);
			socket = new Socket("172.30.1.13", 7777);
			msgView.append("---연결 성공--.\n");
			msgView.append("대기실로 입장하세요.\n");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
		} catch (Exception e) {
			msgView.append(e + "\n\n연결 실패..\n");
		}
	}
 */
}