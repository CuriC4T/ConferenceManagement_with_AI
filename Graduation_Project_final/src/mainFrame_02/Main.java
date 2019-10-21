package mainFrame_02;

import java.awt.Frame;
import java.io.UnsupportedEncodingException;

import javax.swing.SwingUtilities;

import chattingwbclient.ChattingClient;
import voiceClient.GUI;
import voiceClient.RunVoice;
import webCamServer01.WebcamServerSide;
import webcamClient.WebCamClient;
import webcamServer.WebCamServer;

public class Main {
//232323
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new LoginFrame();
		//
		
		// SwingUtilities.invokeLater(new WebcamServerSide());
		// new RunVoice();
		//new WebcamServerSide();

		
		
		// new OrderUI(null);
	}

}
