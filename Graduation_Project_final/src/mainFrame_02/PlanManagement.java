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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import comm.PlanData;

public class PlanManagement  {

	
	public PlanManagement() {
		
	}
	public String returnPlan() {
		String temp="";
		return temp;
	}

	
	/*
	public void showAllPlan() {
		Iterator<PlanData> itr = this.arrayPlanData.iterator();
		while (itr.hasNext()) {
			PlanData temp = (PlanData) itr.next();
			temp.returnDialogue();
		}
		itr = null;
		PlanData temp = null;
	}
	

	public String sendString() {
		String script = "���� ���δ���\n��ȹ ���� �ٸ޴װ��̳׿� ����";
		int scriptsize = this.sayScript.size();

		Random rand = new Random();
		int randnum = rand.nextInt(12) + 1;

		switch (randnum) {
		case 1:
			script = sendTime();
			break;
		case 2:
		case 3:
			int scriptrand = rand.nextInt(scriptsize);
			script = (String) this.sayScript.get(scriptrand);
			break;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			if (this.arrayPlanData.size() != 0)
				script = dayCalcProssess();
			break;
		}

		rand = null;
		return script;
	}

	public String dayCalcProssess() {
		Calendar cal = Calendar.getInstance();

		boolean ans = false;

		int day = cal.get(7);

		Random rand = new Random();
		int num = rand.nextInt(this.arrayPlanData.size());
		PlanData temp = (PlanData) this.arrayPlanData.get(num);
		int planday = temp.returnDay();

		switch (day) {
		case 1:
			if (planday % 17 == 0) {
				ans = importanceCalcProccess(temp.returnImportance());
			}
			break;
		case 2:
			if (planday % 2 == 0) {
				ans = importanceCalcProccess(temp.returnImportance());
			}
			break;
		case 3:
			if (planday % 3 == 0) {
				ans = importanceCalcProccess(temp.returnImportance());
			}
			break;
		case 4:
			if (planday % 5 == 0) {
				ans = importanceCalcProccess(temp.returnImportance());
			}
			break;
		case 5:
			if (planday % 7 == 0) {
				ans = importanceCalcProccess(temp.returnImportance());
			}
			break;
		case 6:
			if (planday % 11 == 0) {
				ans = importanceCalcProccess(temp.returnImportance());
			}
			break;
		case 7:
			if (planday % 13 == 0) {
				ans = importanceCalcProccess(temp.returnImportance());
			}
			break;
		}

		if (ans) {
			String script = temp.returnDialogue();
			return script;
		}

		cal = null;
		temp = null;
		rand = null;
		return null;
	}

	public boolean importanceCalcProccess(int importance) {
		Random rand = new Random();
		int importancerand = rand.nextInt(105) + 1;
		switch (importance) {
		case 1:
			if ((importancerand > 0) && (importancerand < 8)) {
				return true;
			}
			break;
		case 2:
			if ((importancerand > 7) && (importancerand < 22))
				return true;
			break;
		case 3:
			if ((importancerand > 21) && (importancerand < 43))
				return true;
			break;
		case 4:
			if ((importancerand > 42) && (importancerand < 71))
				return true;
			break;
		case 5:
			if ((importancerand > 70) && (importancerand < 106))
				return true;
			break;
		}
		rand = null;
		return false;
	}

	public String sendTime() {
		String time = "";

		String script = "";

		Calendar cal = Calendar.getInstance();
		int hour = cal.get(11);
		int minute = cal.get(12);
		time = "���� �ð��� " + hour + " : " + minute + "�̿���.";

		if ((hour > 22) || ((hour < 3) && (hour > 0))) {
			script = "���ڿ�?";
		}
		if ((hour > 7) && (hour < 9)) {
			script = "��ħ �ȸ����� �ȵǴ� �� ����?";
		}
		if ((hour > 8) && (hour < 11)) {
			script = "���� ��մ� �� ������? ";
		}
		if ((hour > 12) && (hour < 14)) {
			script = "�����ε� ���� ������ �����?";
		}
		if ((hour > 13) && (hour < 18)) {
			script = "(��ǰ)>0< ������ �� �� �ؾ���!!";
		}
		if ((hour > 17) && (hour < 20)) {
			script = "���� �԰� �ؿ� ����!";
		}
		if ((hour > 19) && (hour < 23)) {
			script = "���� ������ ������??";
		}
		time = time + "\n";
		time = time + script;

		cal = null;
		return time;
	}
	*/
}
