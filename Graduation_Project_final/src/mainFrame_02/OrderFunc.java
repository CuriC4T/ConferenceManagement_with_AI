package mainFrame_02;

public class OrderFunc {
	public OrderFunc(String path) {
		Runtime runtime = Runtime.getRuntime();
		String exeFile = path;

		System.out.println("exeFile: " + exeFile);
		Process process;

		try {
			process = runtime.exec(exeFile);
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
