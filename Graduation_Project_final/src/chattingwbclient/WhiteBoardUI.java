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
	private MyCanvas can;// ȭ��Ʈ���� ����

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
		 * ������Ʈ�� ���� �κ�
		 *********************/
		initComponent();
		settingComponent();
		/***********************
		 * ������Ʈ�� �߰�
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

		can = new MyCanvas(); // ��ȭ�� ������ �ϴ� ������Ʈ MyCanvas�� can�� ��� �޴� �ڽ�->���� ���ڱ�
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
		 * ������Ʈ�� ���� �κ�
		 *********************/
		this.setPreferredSize(new Dimension(600,700));
		// setResizable(false);// ������ ���� �Ұ�
		setLayout(new BorderLayout());
		setBackground(new Color(54,54,54));
		//setSize(466,586);
		setBounds(10,70,466,586);
		toolPanel.setPreferredSize(new Dimension(408,120));
		toolPanel.setLayout(new GridLayout(1, 6));
		toolPanel.setBackground(new Color(54,54,54));
		
		can.setBounds(10,70,466, 466);
		//can.setBounds(0,0,466,466);
		can.setBackground(Color.white); // ��ȭ�� ���� �ֱ�
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
		//////////////////////////////// ĵ���� ��ǥ ��
		//////////////////////////////// ó��/////////////////////////////////////
		can.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				// ���콺�� �巡���� ������ x��ǥ,y��ǥ�� ���ͼ� can�� x,y ��ǥ���� �����Ѵ�.
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
		/////////////////////////// ��ư�� �̺�Ʈ ó��
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
				myColor = JColorChooser.showDialog(null, "������", Color.blue); 
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
