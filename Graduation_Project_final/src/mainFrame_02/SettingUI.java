package mainFrame_02;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SettingUI extends JFrame {

	private MainFrame main;
	JButton confirmButton;
	JButton exitButton;

	JPanel IDPWPanel;
	JPanel IDPanel;
	JPanel PasswordPanel;
	JPanel buttonPanel;

	JLabel IDLabel;
	JLabel PasswordLabel;

	JTextField IDField;
	JTextField passField;

	public SettingUI(MainFrame main) {
		this.main = main;

		init();
		settingComponent();
		settingEvent();

		this.add(IDPWPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}

	public void init() {

		confirmButton = new JButton("È®ÀÎ");
		exitButton = new JButton("Ãë¼Ò");

		PasswordPanel = new JPanel();
		IDPanel = new JPanel();
		buttonPanel = new JPanel();

		IDLabel = new JLabel("ID  ");
		PasswordLabel = new JLabel("PW");

		IDField = new JTextField(10);
		passField = new JPasswordField(10);

		setSize(500, 50);
		setUndecorated(true);// Ã¢ ¼³Á¤ ¿©ºÎ
		setResizable(false);// »çÀÌÁî º¯°æ ºÒ°¡
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(50, 50));

	}

	public void settingComponent() {
		IDPWPanel = new JPanel();

		IDPWPanel.setLayout(new GridLayout(2, 2));
		IDPanel.setLayout(new FlowLayout(50));
		PasswordPanel.setLayout(new FlowLayout(50));
		buttonPanel.setLayout(new FlowLayout(50));

		IDPWPanel.setSize(300, 500);

		IDPanel.setSize(100, 100);
		PasswordPanel.setSize(100, 100);

		IDLabel.setFont(new Font("³ª´®°íµñ", Font.PLAIN, (int) 20));
		PasswordLabel.setFont(new Font("³ª´®°íµñ", Font.PLAIN, (int) 20));
		IDField.setFont(new Font("³ª´®°íµñ", Font.PLAIN, (int) 20));
		passField.setFont(new Font("³ª´®°íµñ", Font.PLAIN, (int) 20));

		confirmButton.setSize(40, 40);
		exitButton.setSize(40, 40);

		buttonPanel.add(confirmButton);
		buttonPanel.add(exitButton);

		IDPanel.add(IDLabel);
		IDPanel.add(IDField);

		PasswordPanel.add(PasswordLabel);
		PasswordPanel.add(passField);

		IDPWPanel.add(IDPanel);
		IDPWPanel.add(PasswordPanel);

	}

	public void settingEvent() {
		confirmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				main.setID(IDField.getText());
			}
		});
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}

}
