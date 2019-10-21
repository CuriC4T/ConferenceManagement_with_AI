package mainFrame_02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	/***********************
	 * ������Ʈ�� ���� �κ�
	 *********************/
	// �α��� �κ�//
	private Image screenImage;
	private Graphics screenGraphics;
	
	Image []backGround = new Image[2];

	private String PW;// �α����Ҽ��ִ� ������
	private String ID;// �α����Ҽ��ִ� ���̵�

	int screenWidth;
	int screenHeight;
	int random;

	JPanel IDPanel;
	JPanel PasswordPanel;
	JPanel loginPanel;
	JPanel buttonPanel;

	public LoginFrame() {
		random =new Random().nextInt(2);
		ImageIcon loginID = new ImageIcon(Main.class.getResource("/Images/id.png"));
		ImageIcon loginPass = new ImageIcon(Main.class.getResource("/Images/pass.png"));
		ImageIcon loginButtonImage = new ImageIcon(Main.class.getResource("/Images/LoginEnter.png"));
		ImageIcon loginExitButtonImage = new ImageIcon(Main.class.getResource("/Images/LoginExit.png"));
		ImageIcon loginButtonImage_Cliked = new ImageIcon(Main.class.getResource("/Images/LoginEnter_clicked.png"));
		ImageIcon loginExitButtonImage_Cliked = new ImageIcon(Main.class.getResource("/Images/LoginExit_clicked.png"));

		backGround[0] = new ImageIcon(Main.class.getResource("/Images/login1.gif")).getImage();
		backGround[1] = new ImageIcon(Main.class.getResource("/Images/login2.gif")).getImage();

		JLabel loginLabel = new JLabel();
		JLabel tempLabel = new JLabel();
		
		JLabel IDLabel = new JLabel(loginID);
		JLabel PasswordLabel = new JLabel(loginPass);

		JTextField IDField = new JTextField(10);
		JTextField passField = new JPasswordField(10);

		JButton loginButton = new JButton(loginButtonImage);
		JButton exitButton = new JButton(loginExitButtonImage);
		
		JPanel mainLoginPanel= new JPanel();
	
		IDPanel = new JPanel();
		PasswordPanel = new JPanel();
		loginPanel = new JPanel();
		buttonPanel = new JPanel();
		/***********************
		 * ������Ʈ�� ���� �κ�
		 *********************/
		screenWidth = 600;
		screenHeight = 800;

		setUndecorated(true);// â ���� ����

		setResizable(false);// ������ ���� �Ұ�
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(0,280));// ���̾ƿ� ���� ����
		setBackground(new Color(0, 0, 0, 0));
		setSize(500, 800);

		setLocation((1920 - this.getWidth()) / 2, (1080 - this.getHeight()) / 2);

		IDField.setSize(100, 80);
		passField.setSize(100, 80);

		mainLoginPanel.setSize(100, 80);
		IDPanel.setSize(100, 80);
		PasswordPanel.setSize(100, 80);
		loginPanel.setSize(100, 80);
		buttonPanel.setSize(100, 80);

		mainLoginPanel.setLayout(new BorderLayout(50,50));
		IDPanel.setLayout(new FlowLayout());
		PasswordPanel.setLayout(new FlowLayout());
		loginPanel.setLayout(new BorderLayout(0,10));
		buttonPanel.setLayout(new FlowLayout());

		mainLoginPanel.setBackground(new Color(0, 0, 0, 0));
		IDPanel.setBackground(new Color(0, 0, 0, 0));
		PasswordPanel.setBackground(new Color(0, 0, 0, 0));
		loginPanel.setBackground(new Color(0, 0, 0, 0));
		buttonPanel.setBackground(new Color(0, 0, 0, 0));

		PasswordPanel.setAlignmentX(50);

		IDLabel.setFont(new Font("���", Font.PLAIN, (int) 20));
		IDLabel.setForeground(Color.WHITE);
		PasswordLabel.setFont(new Font("���", Font.PLAIN, (int) 20));
		PasswordLabel.setForeground(Color.WHITE);

		IDField.setFont(new Font("���", Font.PLAIN, (int) 20));
		IDField.setHorizontalAlignment(IDField.CENTER);
		passField.setFont(new Font("���", Font.PLAIN, (int) 20));
		passField.setHorizontalAlignment(passField.CENTER);

		tempLabel.setSize(100, 500);
		loginLabel.setSize(100, 50);
		loginLabel.setHorizontalAlignment(JLabel.CENTER);
		IDLabel.setSize(100, 50);
		PasswordLabel.setSize(100, 50);

		loginButton.setSize(100, 50);
		exitButton.setSize(100, 50);

		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				loginButton.setIcon(loginButtonImage_Cliked);
				loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {

				loginButton.setIcon(loginButtonImage);
				loginButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});

		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				exitButton.setIcon(loginExitButtonImage_Cliked);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {

				exitButton.setIcon(loginExitButtonImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});

		loginButton.setBorderPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.setFocusPainted(false);

		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);

		/***********************
		 * ������Ʈ�� �̺�Ʈ �߰� �κ�
		 *********************/
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String query;
				PW = new String();
				ID = new String();

				ID = IDField.getText();
				PW = passField.getText();
				if(ID!=null) {
					new MainFrame(ID);
					dispose();
				}
				
			}

		});
		
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});

		/***********************
		 * ������Ʈ�� �߰� �κ�
		 *********************/

		IDPanel.add(IDLabel);
		IDPanel.add(IDField);
		PasswordPanel.add(PasswordLabel);
		PasswordPanel.add(passField);

		buttonPanel.add(loginButton);
		buttonPanel.add(exitButton);

		
		
		loginPanel.add(IDPanel, BorderLayout.NORTH);
		loginPanel.add(PasswordPanel, BorderLayout.CENTER);
		
		
		mainLoginPanel.add(tempLabel, BorderLayout.NORTH);
		mainLoginPanel.add(loginPanel, BorderLayout.CENTER);

		add(loginLabel, BorderLayout.NORTH);
		add(mainLoginPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setVisible(true);
		//pack();

	}

	

	public void paint(Graphics g) {
		screenImage = createImage(600, 800);
		screenGraphics = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphics);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(backGround[random], 0, 0, null);
		// g.drawImage(blurImage, 0, 0, null);

		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		paintComponents(g);
		this.repaint();
	}

}
