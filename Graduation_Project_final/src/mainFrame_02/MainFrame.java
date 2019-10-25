package mainFrame_02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import cryption.CipherFunc;

//3e454c 2185c5 7ecefd fff6e5 ff7f66
public class MainFrame extends JFrame {
	private Image screenImage;
	private Graphics screenGraphics;

	private String ID;
	private Dimension MONITER_SIZE;
	static ImageIcon AIimage;
	static ImageIcon AI_Thinking;
	static ImageIcon AIimage2;

	private ImageIcon micOnimage;
	private ImageIcon micOffimage;
	private JLabel AILabel;

	private JPopupMenu popupMenu;

	private MainFrame frame;
	private JPanel orderPart;
	private JLabel whatAIsay;
	private JLabel whatIsay;
	private JTextField orderField;
	private TTS tts ;
	private STT stt;
	private AI ai;

	JPanel AIPanel = new JPanel();
	JPanel STTPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JButton STTStartButton = new JButton();
	JButton STTEndButton = new JButton();

	int mouseX, mouseY;
	private int imageWidth, imageHeight;

	String AIspeck="";
	
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	CipherFunc cipher;
	
	public MainFrame(String ID) {
		this.ID=ID;
		frame = this;
		
		try {
			cipher = new CipherFunc();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MONITER_SIZE = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		AIimage = new ImageIcon(Main.class.getResource("../Images/AI.gif"));
		AI_Thinking = new ImageIcon(Main.class.getResource("../Images/AIThinking.gif"));
		AIimage2= new ImageIcon(Main.class.getResource("../Images/AI2.gif"));
		micOnimage = new ImageIcon(Main.class.getResource("../Images/micon.png"));
		micOffimage = new ImageIcon(Main.class.getResource("../Images/micoff.png"));

		AILabel = new JLabel(AIimage);

		orderPart = new JPanel();

		whatIsay = new JLabel();
		whatAIsay = new JLabel();

		orderField = new JTextField(10);
		
		AIPanel = new JPanel();
		STTPanel = new JPanel();
		inputPanel = new JPanel();
		STTStartButton = new JButton(micOnimage);
		STTEndButton = new JButton(micOffimage);

		imageWidth = AIimage.getIconWidth();
		imageHeight = AIimage.getIconHeight();

		mouseX = imageWidth / 2;
		mouseY = imageHeight / 2;

		setUndecorated(true);
		setSize(imageWidth, imageHeight + 200);
		setLocation((this.MONITER_SIZE.width - getWidth()) / 2, (this.MONITER_SIZE.height - getHeight()) / 2);
		setLayout(new BorderLayout());
		setResizable(false);
		this.setBackground(Color.BLACK);
		setAlwaysOnTop(true);
		// setOpacity(0.9F);

		init();
		settingEvent();
		popupMenu();

		connect();
		setVisible(true);
		pack();
		tts.speak(AIspeck);
	}

	public void init() {

		JPanel temp = new JPanel();
		tts= new TTS();
		ai = new AI(this, whatAIsay,tts);
		stt = new STT(whatIsay, ai);
		
		STTStartButton.setPreferredSize(new Dimension(40,40));
		STTStartButton.setBorderPainted(false);
		STTStartButton.setContentAreaFilled(false);
		STTStartButton.setFocusPainted(false);
		STTStartButton.setEnabled(false);
		
		STTEndButton.setPreferredSize(new Dimension(40,40));
		STTEndButton.setEnabled(true);
		STTEndButton.setBorderPainted(false);
		STTEndButton.setContentAreaFilled(false);
		STTEndButton.setFocusPainted(false);
		
		AILabel.setSize(AIimage.getIconWidth(), AIimage.getIconHeight());
		AILabel.setOpaque(false);
		AILabel.setBackground(new Color(54,54,54));

		temp.setBackground(new Color(54,54,54));
		temp.setPreferredSize(new Dimension(50, 60));

		STTPanel.setLayout(new FlowLayout());
		STTPanel.setBackground(new Color(54,54,54));

		AIPanel.setSize(AIimage.getIconWidth(), AIimage.getIconHeight());
		AIPanel.setBackground(new Color(54,54,54));

		inputPanel.setLayout(new BorderLayout());
		inputPanel.setSize(30, 40);
		inputPanel.setBackground(new Color(54,54,54));

		orderPart.setLayout(new BorderLayout(10, 30));
		orderPart.setSize(30, 200);
		orderPart.setBackground(new Color(54,54,54));
		
		
		whatAIsay.setFont(new Font("³ª´®°íµñ", Font.PLAIN, (int) 50));
		whatAIsay.setHorizontalAlignment(JLabel.CENTER);
		AIspeck=" ¾È³çÇÏ¼¼¿ä. "+ID+"´Ô ";
		whatAIsay.setText(AIspeck);
		whatAIsay.setBackground(new Color(54,54,54));
		whatAIsay.setForeground(new Color(0xfff6e5));

		whatIsay.setHorizontalAlignment(JLabel.CENTER);
		whatIsay.setBackground(new Color(54,54,54));
		whatIsay.setFont(new Font("³ª´®°íµñ", Font.PLAIN, (int) 20));
		whatIsay.setForeground(new Color(0xfff6e5));
		whatIsay.setText(" ");

		orderField.setHorizontalAlignment(JLabel.CENTER);
		orderField.setToolTipText("¸í·É¾î¸¦ ÀÔ·ÂÇÏ¼¼¿ä");
		orderField.setFont(new Font("³ª´®°íµñ", Font.PLAIN, (int) 30));

		AIPanel.add(AILabel);
		STTPanel.add(STTStartButton);
		STTPanel.add(STTEndButton);

		inputPanel.add(orderField, BorderLayout.CENTER);
		inputPanel.add(STTPanel, BorderLayout.EAST);

		orderPart.add(whatAIsay, BorderLayout.NORTH);
		orderPart.add(whatIsay, BorderLayout.CENTER);
		orderPart.add(inputPanel, BorderLayout.SOUTH);

		add(AIPanel, BorderLayout.NORTH);
		add(temp, BorderLayout.CENTER);
		add(orderPart, BorderLayout.SOUTH);
		stt.startSTT();
	}

	public void settingEvent() {
		AILabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseX = e.getX();
					mouseY = e.getY();

				}
				if (e.getButton() == MouseEvent.BUTTON3) {

					popupMenu.show(e.getComponent(), mouseX, mouseY);
				}
			}

			public void mouseReleased(MouseEvent e) {
			}
		});

		AILabel.addMouseMotionListener(new MouseAdapter() {

			public void mouseDragged(MouseEvent e) {

				int X = e.getXOnScreen();
				int Y = e.getYOnScreen();

				frame.setLocation(X - mouseX, Y - mouseY);
			}
		});
		orderField.addActionListener(new ActionListener() {
			String order;

			@Override
			public void actionPerformed(ActionEvent e) {
				order = orderField.getText();
				orderField.setText("");
				whatIsay.setText(order);
				ai.performOrder(order);

			}

		});

		STTStartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AILabel.setIcon(AI_Thinking);
				STTStartButton.setEnabled(false);
				STTEndButton.setEnabled(true);
				stt.startSTT();
			}

		});
		STTEndButton.addActionListener(new ActionListener() {
			String order;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AILabel.setIcon(AIimage);
				STTStartButton.setEnabled(true);
				STTEndButton.setEnabled(false);
				stt.stopSTT();
				order = whatIsay.getText();
				

			}

		});
	}

	public void popupMenu() {
		popupMenu = new JPopupMenu();
		JMenuItem orders = new JMenuItem("¸í·É");
		JMenuItem managerplan = new JMenuItem("ÀÏÁ¤");
		JMenuItem setting = new JMenuItem("¼³Á¤");
		JMenuItem help = new JMenuItem("µµ¿ò¸»");
		JMenuItem exit = new JMenuItem("Á¾·á");

		orders.setFont(new Font("HY°ß°íµñ", 0, 15));
		managerplan.setFont(new Font("HY°ß°íµñ", 0, 15));
		setting.setFont(new Font("HY°ß°íµñ", 0, 15));
		help.setFont(new Font("HY°ß°íµñ", 0, 15));
		exit.setFont(new Font("HY°ß°íµñ", 0, 15));

		orders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OrderUI(ai.getDataManagement());

			}
		});
		managerplan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PlanManagerFrame(ai.getDataManagement());
			}
		});
		setting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SettingUI(frame);
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// new PlanPartner.helpFrame(PlanPartner.this);
				System.out.println(ID);
			}

		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		popupMenu.add(managerplan);
		popupMenu.add(orders);
		popupMenu.add(setting);
		popupMenu.add(help);
		popupMenu.add(exit);

	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public JLabel getWhatIsay() {
		return whatIsay;
	}

	public JLabel getWhatAIsay() {
		return whatAIsay;
	}
	void connect() { // ¿¬°á
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			System.out.println(inetAddress);
			socket = new Socket(inetAddress, 7777);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println(cipher.encrypt("[NAME]"+ID));
		} catch (Exception e) {
			
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public CipherFunc getCipher() {
		return cipher;
	}

	public void setCipher(CipherFunc cipher) {
		this.cipher = cipher;
	}

	public JLabel getAILabel() {
		return AILabel;
	}

	public void setAILabel(JLabel aILabel) {
		AILabel = aILabel;
	}

}
