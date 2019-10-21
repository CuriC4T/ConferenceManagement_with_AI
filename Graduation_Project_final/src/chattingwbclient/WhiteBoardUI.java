package chattingwbclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainFrame_02.Main;

public class WhiteBoardUI extends JPanel {
	private int X, Y;
	private MyCanvas can;// 화이트보드 메인

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

	private Color personColor;
	private Color myColor;

	private String ID;
	

	PrintWriter printWriter;
	
	public WhiteBoardUI(String ID, PrintWriter printWriter) {
		this.ID = ID;
		this.printWriter=printWriter;
		/***********************
		 * 컴포넌트들 선언 부분
		 *********************/
		initComponent();
		settingComponent();
		/***********************
		 * 컴포넌트들 추가
		 *********************/
		toolPanel.add(erase);
		toolPanel.add(allclear);
		toolPanel.add(changeColorButton);
		toolPanel.add(largerPen);
		toolPanel.add(smallerPen);
		toolPanel.add(exit);
		
		add(can,BorderLayout.CENTER);
		add(toolPanel,BorderLayout.SOUTH);
		
		setVisible(true);

	}

	public void initComponent() {
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

		can = new MyCanvas(); // 도화지 역할을 하는 컴포넌트 MyCanvas는 can을 상속 받는 자식->원이 갑자기
		exit = new JButton(exitIcon);
		erase = new JButton(eraseIcon);
		allclear = new JButton(allclearIcon);
		changeColorButton = new JButton(changeColorButtonIcon);
		largerPen = new JButton(largerPenIcon);
		smallerPen = new JButton(smallerPenIcon);
		myColor = Color.BLACK;
		personColor = Color.BLACK;
		toolPanel = new JPanel();
		
		/***********************
		 * 컴포넌트들 세팅 부분
		 *********************/
		this.setPreferredSize(new Dimension(600,700));
		// setResizable(false);// 사이즈 변경 불가
		setLayout(new BorderLayout());
		setBackground(new Color(54,54,54));
		//setSize(466,586);
		setBounds(10,70,466,586);
		toolPanel.setPreferredSize(new Dimension(408,120));
		toolPanel.setLayout(new GridLayout(1, 6));
		toolPanel.setBackground(new Color(54,54,54));
		
		can.setBounds(10,70,466, 466);
		//can.setBounds(0,0,466,466);
		can.setBackground(Color.white); // 도화지 배경색 주기
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
		//////////////////////////////// 캔버스 좌표 값
		//////////////////////////////// 처리/////////////////////////////////////
		can.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				// 마우스를 드래그한 지점의 x좌표,y좌표를 얻어와서 can의 x,y 좌표값에 전달한다.
				X = e.getX();
				Y = e.getY();

				printWriter.println("[WHITE]" + "#" + X + "#" + Y + "#" + myColor.getRGB() + "#" + can.getW() + "#" + can.getH());

				can.repaint();

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}

		});
		/////////////////////////// 버튼들 이벤트 처리
		largerPen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyCanvas can2 = (MyCanvas) can;
				can2.w += 10;
				can2.h += 10;
			}

		});
		smallerPen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyCanvas can2 = (MyCanvas) can;
				if (can.w > 10 && can.h > 10) {
					can2.w -= 10;
					can2.h -= 10;
				}

			}

		});

		erase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				printWriter.println("[WHITE]" + "#" + "allclear" + "#" + 0 + "#" + 0 + "#" + 0 + "#" + 0);
			}

		});
		changeColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyCanvas can2 = (MyCanvas) can;
				myColor = JColorChooser.showDialog(null, "색선정", Color.blue); 
				can2.cr = myColor;
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				printWriter.println("[EXIT]" + ID);
				//main.dispose();
			}

		});
	}

	public int getX() {
		return X;

	}

	public int getY() {
		return Y;

	}

	public MyCanvas getMyCanvas() {

		return can;
	}

	public Color getPersonColor() {

		return personColor;
	}

	public Color getMyColor() {

		return myColor;
	}

	public MyCanvas getCanvas() {

		return can;
	}

}
