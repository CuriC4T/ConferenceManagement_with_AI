package database;

public class DTO {

	private int ID;
	private String classification;
	private String order;
	
	public DTO(int ID,String classification,String order) {
		this.ID=ID;
		this.classification=classification;
		this.order=order;
	}
	public DTO(String classification,String order) {
		this.classification=classification;
		this.order=order;
	}
	public DTO() {
		
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String price) {
		this.order = price;
	}
}
