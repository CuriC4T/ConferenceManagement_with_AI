package mainFrame_02;

import java.io.IOException;

public class SearchFunc {
	
	public SearchFunc() {
	}
	public boolean Searching(String search) {
		boolean complete=false;
		try {
			int idx = search.indexOf(" °Ë»ö");
			String searching = search.substring(0, idx);
			searching = searching.replaceAll(" ", "");

			java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://google.com/search?h1=kr&q=" + searching));
			complete=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			complete=false;
		}
		return complete;
	}
}
