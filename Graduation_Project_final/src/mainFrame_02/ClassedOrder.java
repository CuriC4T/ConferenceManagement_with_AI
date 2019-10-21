package mainFrame_02;

import java.util.Vector;

public class ClassedOrder extends Vector {

	private String classification;
	//private String specificOrder;

	
	public void add(String order) {
		super.add(order);
	}
	public	void remove(String order) { 
		super.remove(order);
	}
	public String getOrder(int i) { 
		return (String) elementAt(i);
	}
	public ClassedOrder(String classification) {
			this.classification = classification;
		}

	public boolean checkOrder(String order) {
		for (int i = 0; i < size(); i++) {
			if(getOrder(i).equals(order)) return true;
		}
		return false;
	}
	

	public String getClassification() {
		return classification;

	}

	

	public void setClassification(String classification) {
		this.classification = classification;

	}


}
