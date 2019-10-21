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

	private JTextArea msgView = new JTextArea("", 1, 1); // �޽����� �����ִ� ����
	private JTextField sendBox = new JTextField(""); // ���� �޽����� ���� ����
	private JTextField nameBox = new JTextField(); // ����� �̸� ����
	private JTextField roomBox = new JTextField("0"); // �� ��ȣ ����

	// �濡 ������ �ο��� ���� �����ִ� ���̺�
	private JLabel pInfo = new JLabel("����:  ��");

	private java.awt.List pList = new java.awt.List(); // ����� ����� �����ִ� ����Ʈ
	private JButton startButton = new JButton("��ķ �ѱ�"); // ��ķ ���� ��ư
	private JButton WebcamButton = new JButton("��ķ ����"); // ��ķ ���� ��ư
	private JButton stopButton = new JButton("��ķ ����"); // ��ķ ���� ��ư
	private JButton enterButton = new JButton("�����ϱ�"); // �����ϱ� ��ư
	private JButton exitButton = new JButton("���Ƿ�"); // ���Ƿ� ��ư

	// ���� ������ �����ִ� ���̺�
	private JLabel infoView = new JLabel("�ȳ��ϼ���");

	private BufferedReader reader; // �Է� ��Ʈ��
	private PrintWriter writer; // ��� ��Ʈ��
	private Socket socket; // ����
	private int roomNumber = -1; // �� ��ȣ
	private String userName = null; // ����� �̸�
	private MyCanvas can;
	private MainFrame mainframe;
	/**
	 * Generator function of class
	 * 
	 * @param userName name of user
	 * @throws UnsupportedEncodingException
	 */
	public ChattingClient(MainFrame mainframe) throws UnsupportedEncodingException { // ������
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
		setResizable(false); // size ���� �Ұ�
		setLocationRelativeTo(null);
		setSize(785, 625);
		setLayout(null); // ���̾ƿ��� ������� �ʴ´�.
		setBackground(new Color(50, 50, 50));

		// ���� ������Ʈ�� �����ϰ� ��ġ�Ѵ�.
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
		p.add(new JLabel("��     ��:", 2));
		p.add(nameBox);
		p.add(new JLabel("�� ��ȣ:", 2));
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

		// �̺�Ʈ �����ʸ� ����Ѵ�.
		sendBox.addActionListener(this);
		enterButton.addActionListener(this);
		exitButton.addActionListener(this);
		startButton.addActionListener(this);
		WebcamButton.addActionListener(this);

		stopButton.addActionListener(this);

		// ������ �ݱ� ó��
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
		erase.setSize(68, 80);// ���찳
		allclear.setSize(68, 80);// �� �����
		changeColorButton.setSize(68, 80);// ���� �ٲٱ�
		largerPen.setSize(68, 80);// �� ũ��
		smallerPen.setSize(68, 80);// �� �۰�

		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		erase.setBorderPainted(false);
		erase.setContentAreaFilled(false);
		erase.setFocusPainted(false);
		////////////////// ��ư �����ϰ�,�׵θ� ��� �����
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
		 * ������Ʈ�� �̺�Ʈ ó��
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
		 * ĵ���� ��ǥ �� ó��
		 */
		can.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				// ���콺�� �巡���� ������ x��ǥ,y��ǥ�� ���ͼ� can�� x,y ��ǥ���� �����Ѵ�.
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
		// ��ư�� �̺�Ʈ ó��
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
				myColor = JColorChooser.showDialog(null, "������", Color.blue);
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

	// ������Ʈ���� �׼� �̺�Ʈ ó��
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == sendBox) { // �޽��� �Է� �����̸�
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

		else if (ae.getSource() == enterButton) { // �����ϱ� ��ư�̸�
			try {
				if (Integer.parseInt(roomBox.getText()) < 1) {
					infoView.setText("���ȣ�� �߸��Ǿ����ϴ�. 1�̻�");
					JOptionPane.showMessageDialog(null, "���ȣ�� �߸��Ǿ����ϴ�. 1�̻�", "���ȣ", JOptionPane.WARNING_MESSAGE);
					return;
				}
				MyCanvas can2 = (MyCanvas) can;
				Graphics g = can2.getGraphics();
				g.clearRect(0, 0, can.getWidth(), can.getHeight());
				writer.println(cipher.encrypt("[ROOM]" + Integer.parseInt(roomBox.getText())));
				msgView.setText("");
			} catch (Exception ie) {
				infoView.setText("�Է��Ͻ� ���׿� ������ �ҽ��ϴ�.");
				JOptionPane.showMessageDialog(null, "�Է��Ͻ� ���׿� ������ �ҽ��ϴ�.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (ae.getSource() == exitButton) { // ���Ƿ� ��ư�̸�
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
				infoView.setText("����� ������ ��ٸ��ϴ�.");
				startButton.setEnabled(false);
				new Frame("asd").setVisible(true);
				// WebCamServer aa =new WebCamServer();
			} catch (Exception e) {
			}
		} else if (ae.getSource() == WebcamButton) {
			try {
				// writer.println("[START]");
				infoView.setText("����� ������ ��ٸ��ϴ�.");
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

	void goToWaitRoom() { // ���Ƿ� ��ư�� ������ ȣ��ȴ�.
		try {
			writer.println(cipher.encrypt("[NAME]" + userName));
			msgView.setText("");
			writer.println(cipher.encrypt("[ROOM]0"));
			infoView.setText("���ǿ� �����ϼ̽��ϴ�.");
			// JOptionPane.showMessageDialog(null, "���ǿ� �����ϼ̽��ϴ�.");
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
		String msg; // �����κ����� �޽���
		try {
			while ((msg = reader.readLine()) != null) {
				//System.out.println(msg);
				if (msg.startsWith("[ROOM]")) { // �濡 ����
					if (!msg.equals("[ROOM]0")) { // ������ �ƴ� ���̸�
						enterButton.setEnabled(false);
						exitButton.setEnabled(true);
						infoView.setText(msg.substring(6) + "�� �濡 �����ϼ̽��ϴ�.");
						JOptionPane.showMessageDialog(null, msg.substring(6) + "�� �濡 �����ϼ̽��ϴ�.");
					} else {
						infoView.setText("���ǿ� �����ϼ̽��ϴ�.");
						JOptionPane.showMessageDialog(null, "���ǿ� �����ϼ̽��ϴ�.");
					}

					roomNumber = Integer.parseInt(msg.substring(6)); // �� ��ȣ ����

				}

				else if (msg.startsWith("[PLAYERS]")) { // �濡 �ִ� ����� ���
					nameList(msg.substring(9));
				}

				else if (msg.startsWith("[ENTER]")) { // �մ� ����
					pList.add(msg.substring(7));
					playersInfo();
					msgView.append("[" + msg.substring(7) + "]���� �����Ͽ����ϴ�.\n");
				}
				else if (msg.startsWith("[ALRAM]")) { 
					msg=msg.substring(7);
					JOptionPane.showMessageDialog(null, msg+"���� ȣ���Դϴ�.");
					
				}

				else if (msg.startsWith("[EXIT]")) { // �մ� ����
					pList.remove(msg.substring(6)); // ����Ʈ���� ����
					playersInfo(); // �ο����� �ٽ� ����Ͽ� �����ش�.
					msgView.append("[" + msg.substring(6) + "]���� �ٸ� ������ �����Ͽ����ϴ�.\n");
					if (roomNumber != 0) {
						endWebcam("��밡 �������ϴ�.");
						JOptionPane.showMessageDialog(null, "��밡 �������ϴ�.");
					}
				}

				else if (msg.startsWith("[DISCONNECT]")) { // �մ� ���� ����
					pList.remove(msg.substring(12));
					playersInfo();
					msgView.append("[" + msg.substring(12) + "]���� ������ �������ϴ�.\n");
					if (roomNumber != 0) {
						endWebcam("��밡 �������ϴ�.");
						JOptionPane.showMessageDialog(null, "��밡 �������ϴ�.");
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
					msgView.append("[" + msg.substring(12) + "]���� ������ �������ϴ�.\n");
					if (roomNumber != 0) {
						JOptionPane.showMessageDialog(null, "��밡 �������ϴ�.");
					}

				}

				// ��ӵ� �޽����� �ƴϸ� �޽��� ������ �����ش�.
				else
					msgView.append(msg + "\n");
			}
		} catch (IOException ie) {
			msgView.append(ie + "\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msgView.append("������ ������ϴ�.");
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
		} // 2�ʰ� ���

		// if (board.isRunning())
		// board.webcam();
		// if (pList.getItemCount() == 2)
		// startButton.setEnabled(true);
	}

	/**
	 * Show number of Player
	 */
	private void playersInfo() { // �濡 �ִ� �������� ���� �����ش�.
		int count = pList.getItemCount();
		if (roomNumber == 0)
			pInfo.setText("����: " + count + "��");
		else
			pInfo.setText(roomNumber + " �� ��: " + count + "��");

		// if (count == 2 && roomNumber != 0)
		// startButton.setEnabled(true);
		// else
		// startButton.setEnabled(false);
	}

	// ����� ����Ʈ���� ����ڵ��� �����Ͽ� pList�� �߰��Ѵ�.
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
	
	void connect() { // ����
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			msgView.append("������ ������ ��û�մϴ�.\n");
			System.out.println(inetAddress);
			socket = new Socket("172.30.1.13", 7777);
			msgView.append("---���� ����--.\n");
			msgView.append("���Ƿ� �����ϼ���.\n");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
		} catch (Exception e) {
			msgView.append(e + "\n\n���� ����..\n");
		}
	}
 */
}