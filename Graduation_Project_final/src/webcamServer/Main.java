package webcamServer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			WebcamServer aa=new WebcamServer();
			Thread th = new Thread(aa);
			th.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
