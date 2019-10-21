package comm;

import java.io.Serializable;

public class orderData  implements Serializable{
	
	private String orderName;
	private String path;

	public orderData(String orderName, String path) {
		
		this.orderName=orderName;
		this.path=path;
	}

	

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
