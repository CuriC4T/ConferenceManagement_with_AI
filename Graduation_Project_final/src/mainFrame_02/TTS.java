package mainFrame_02;

import java.io.IOException;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class TTS {
//AIzaSyCSLfyt9djpCL2Ov44y8A8Imxe5xaS4Pmg
	//AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw
	SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	
	public TTS() {}

	public void speak(String text) {
		System.out.println(text);

		Thread thread = new Thread(() -> {
			try {
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
				player.play();
				System.out.println("Successfully got back synthesizer data");
			} catch (IOException | JavaLayerException e) {

				e.printStackTrace();

			}
		});

		thread.setDaemon(false);
		thread.start();

	}

	

	

}
