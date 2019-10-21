package mainFrame_02;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

class PlanManagerFrame extends JFrame {
	String name;
	String dialogue;
	int day = 1;
	int importan = 1;
	DataManagement dataMenagement;

	public PlanManagerFrame(DataManagement dataMenagement) {
		this.dataMenagement = dataMenagement;
		String[] IMPORTANCE = { "1", "2", "3", "4", "5" };

		JRadioButton monButton = new JRadioButton("월");
		JRadioButton tuesButton = new JRadioButton("화");
		JRadioButton wednButton = new JRadioButton("수");
		JRadioButton thurButton = new JRadioButton("목");
		JRadioButton friButton = new JRadioButton("금");
		JRadioButton satButton = new JRadioButton("토");
		JRadioButton sunButton = new JRadioButton("일");

		JButton addButton = new JButton("추가");
		JButton cancelButton = new JButton("취소");
		ButtonGroup dayButton = new ButtonGroup();

		JLabel writeName = new JLabel("계획 이름:");
		JLabel writedialogue = new JLabel("대사를 입력하세요.:");
		JLabel setimportan = new JLabel("중요도:");
		JLabel setdeadline = new JLabel("마감일:");
		JLabel setday = new JLabel("해당 요일을 체크하세요.");
		JComboBox importance = new JComboBox(IMPORTANCE);

		JTextField planName = new JTextField();
		planName.setFont(new Font("돋음", 0, 15));

		JTextArea planDialogue = new JTextArea();
		planDialogue.setLineWrap(true);
		planDialogue.setFont(new Font("돋음", 0, 15));
		planDialogue.setBackground(new Color(16764108));

		Border dayGroup = BorderFactory.createEtchedBorder();
		dayGroup = BorderFactory.createTitledBorder(dayGroup, "요일");
		Border dialogueGroup = BorderFactory.createEtchedBorder();
		dialogueGroup = BorderFactory.createTitledBorder(dialogueGroup, "대사");
		Border importanceGroup = BorderFactory.createEtchedBorder();
		importanceGroup = BorderFactory.createTitledBorder(importanceGroup, "중요도");
		JPanel dayBorderPanel = new JPanel();

		dayBorderPanel.setLayout(new GridLayout(1, 7));
		dayBorderPanel.setBorder(dayGroup);
		dayBorderPanel.add(monButton);
		dayBorderPanel.add(tuesButton);
		dayBorderPanel.add(wednButton);
		dayBorderPanel.add(thurButton);
		dayBorderPanel.add(friButton);
		dayBorderPanel.add(satButton);
		dayBorderPanel.add(sunButton);

		planName.addKeyListener(new java.awt.event.KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (planName.getText().equals("")) {
					addButton.setEnabled(false);
				} else {
					addButton.setEnabled(true);
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel dialogueBorderPanel = new JPanel();
		dialogueBorderPanel.setLayout(new GridLayout(1, 0));
		dialogueBorderPanel.setBorder(dialogueGroup);
		dialogueBorderPanel.add(planDialogue);

		JPanel importanceBorderPanel = new JPanel();
		importanceBorderPanel.setLayout(new GridLayout(1, 1));
		importanceBorderPanel.setBorder(importanceGroup);
		importanceBorderPanel.add(importance);

		addButton.setEnabled(false);

		addButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (addButton.isEnabled()) {

					name = planName.getText();

					dialogue = planDialogue.getText();
					if (monButton.isSelected()) {
						day *= 2;
					}
					if (tuesButton.isSelected()) {
						day *= 3;
					}
					if (wednButton.isSelected()) {
						day *= 5;
					}
					if (thurButton.isSelected()) {
						day *= 7;
					}
					if (friButton.isSelected()) {
						day *= 11;
					}
					if (satButton.isSelected()) {
						day *= 13;
					}
					if (sunButton.isSelected()) {
						day *= 17;
					}

					if (day == 1) {
						day = 510510;
					}

					dataMenagement.addPlanData(name, dialogue, importan, day);
					dataMenagement.savePlanToFile();

					dispose();
				}

			}
		});
		importance.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					// PlanPartner.PlanManagerFrame.this.importan =
					// Integer.valueOf(e.getItem().toString()).intValue();
				}

			}

		});
		setSize(400, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setLayout(null);

		writeName.setBounds(20, 40, 80, 30);
		planName.setBounds(80, 40, 100, 30);

		writedialogue.setBounds(20, 80, 200, 30);
		dialogueBorderPanel.setBounds(20, 110, 300, 100);

		setdeadline.setBounds(20, 220, 100, 30);

		setimportan.setBounds(300, 220, 100, 30);
		importanceBorderPanel.setBounds(300, 250, 80, 50);

		setday.setBounds(20, 280, 400, 100);
		dayBorderPanel.setBounds(50, 350, 300, 100);

		addButton.setBounds(20, 480, 100, 50);
		cancelButton.setBounds(280, 480, 100, 50);

		getContentPane().add(importanceBorderPanel);
		getContentPane().add(dialogueBorderPanel);
		getContentPane().add(planName);
		getContentPane().add(dayBorderPanel);
		getContentPane().add(writeName);
		getContentPane().add(writedialogue);
		getContentPane().add(setimportan);
		getContentPane().add(setdeadline);
		getContentPane().add(setday);
		getContentPane().add(addButton);
		getContentPane().add(cancelButton);
		setVisible(true);
	}
}
