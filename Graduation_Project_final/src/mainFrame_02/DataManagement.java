package mainFrame_02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import comm.PlanData;
import comm.orderData;

public class DataManagement {

	private File dataFile;
	private File planFile;
	private FileInputStream inputfile;
	private ObjectInputStream readfile;

	private FileOutputStream Outputfile;
	private ObjectOutputStream savefile;

	private ArrayList<orderData> arrayOrderData;
	private ArrayList<PlanData> arrayPlanData;
	public DataManagement() {

		dataFile = new File("data.data");
		planFile = new File("plan.data");
		arrayOrderData = new ArrayList();
		arrayPlanData = new ArrayList();
	}

	public void loadDataFromFile() {
		try {
			inputfile = new FileInputStream(dataFile);
			readfile = new ObjectInputStream(inputfile);
			for (;;) {
				orderData temp = (orderData) readfile.readObject();
				if (temp == null)
					break;
				arrayOrderData.add(temp);
			}

			inputfile.close();
			readfile.close();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		
		try {
			FileInputStream inputfile = new FileInputStream(planFile);
			ObjectInputStream readfile = new ObjectInputStream(inputfile);
			for (;;) {
				PlanData temp = (PlanData) readfile.readObject();
				if (temp == null)
					break;
				this.arrayPlanData.add(temp);
			}

			inputfile.close();
			readfile.close();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		
	}
	public void savePlanToFile() {
		try {
			FileOutputStream Outputfile = new FileOutputStream(planFile);
			ObjectOutputStream savefile = new ObjectOutputStream(Outputfile);
			Iterator<PlanData> itr = this.arrayPlanData.iterator();
			while (itr.hasNext()) {
				savefile.writeObject(itr.next());
			}
			Outputfile.close();
			savefile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveOrderToFile() {
		try {
			Outputfile = new FileOutputStream(dataFile);
			savefile = new ObjectOutputStream(Outputfile);
			Iterator<orderData> itr = arrayOrderData.iterator();
			while (itr.hasNext()) {
				savefile.writeObject(itr.next());
			}
			Outputfile.close();
			savefile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addOrderData(int index, orderData data) {
		arrayOrderData.add(data);
	}

	public void deleteOrderData(int index) {
		arrayOrderData.remove(index);

	}

	public orderData getOrderData(int index) {

		return arrayOrderData.get(index);
	}

	public ArrayList<orderData> getArrayOrderData() {

		return arrayOrderData;
	}

	public boolean canDoOrder(String order) {
		Iterator<orderData> itr = arrayOrderData.iterator();
		while (itr.hasNext()) {
			orderData temp = (orderData) itr.next();
			if (order.contains(temp.getOrderName())) {
				new OrderFunc(temp.getPath());
				return true;
			}
		}
		return false;
	}
	public void addPlanData(String name, String dialogue, int importance, int day) {
		arrayPlanData.add(new PlanData(name, dialogue, importance, day));
	}

	public void deletePlanData(String name, String dialogue, int day, int importance) {
		Iterator<PlanData> itr = this.arrayPlanData.iterator();

		while (itr.hasNext()) {
			PlanData temp = (PlanData) itr.next();

			if ((temp.returnName().equals(name)) && (temp.returnDialogue().equals(dialogue))
					&& (temp.returnImportance() == importance) && (temp.returnDay() == day)) {
				itr.remove();
				break;
			}
		}
		itr = null;
		PlanData temp = null;
		savePlanToFile();
	}

	public ArrayList<PlanData> getArrayPlanData() {
		return arrayPlanData;
	}

	public void setArrayPlanData(ArrayList<PlanData> arrayPlanData) {
		this.arrayPlanData = arrayPlanData;
	}

	
	

	
}
