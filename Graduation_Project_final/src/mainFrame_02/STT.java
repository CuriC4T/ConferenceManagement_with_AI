package mainFrame_02;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

import net.sourceforge.javaflacencoder.FLACFileWriter;

public class STT implements GSpeechResponseListener {

	final Microphone mic = new Microphone(FLACFileWriter.FLAC);

	// my key AIzaSyBHzkRdzR7A6mONZK_BeWFnl6szkVFpy0c
	// my key AIzaSyBHzkRdzR7A6mONZK_BeWFnl6szkVFpy0c
	// test AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw
	GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	JLabel label;
	AI ai;
	String output = "";
	
	public STT(JLabel label, AI ai) {
		this.label = label;
		this.ai = ai;

		duplex.setLanguage("ko-kr");
		duplex.addResponseListener(new GSpeechResponseListener() {
			String old_text = "";
			public void onResponse(GoogleResponse gr) {
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = label.getText();
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = (label.getText() + "\n");
					this.old_text = this.old_text.replace(")", "").replace("( ", "");
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				if (gr.isFinalResponse()) {
					label.setText(output);
					ai.performOrder(output);
					System.out.println("명령어 수행");
				}
			}
		});
	}

	public void startSTT() {
		new Thread(() -> {
			try {
				duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}).start();
	}

	public void stopSTT() {
		mic.close();
		duplex.stopSpeechRecognition();

	}

	@Override
	public void onResponse(GoogleResponse paramGoogleResponse) {
		// TODO Auto-generated method stub

	}
}
