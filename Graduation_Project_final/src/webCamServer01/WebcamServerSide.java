package webCamServer01;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class WebcamServerSide extends JFrame {

	public JPanel panel;
	public int xpos = 0, ypos = 0;
	public Webcam webcam;

	// Constructor
	public WebcamServerSide() {

		webcam = Webcam.getDefault();
		webcam.setViewSize(new Dimension(640, 480));
		if (webcam.isOpen() == false) {
			webcam.open();
		}
		WebcamPanel webcamPanel = new WebcamPanel(webcam);
		webcamPanel.setSize(640, 320);

		// Creating a panel that while contains the canvas
		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		//panel.add(webcamPanel, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 0);
		setSize(640, 900);
		setAlwaysOnTop(true);

		add(webcamPanel);
		xpos = getX();
		ypos = getY();

		// Playing the video

		try {
			// new ServerSetting(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);

		}
	}
}
