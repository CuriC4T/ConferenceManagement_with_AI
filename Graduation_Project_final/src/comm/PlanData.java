package comm;

import java.io.Serializable;

public class PlanData implements Serializable {
	private String name;
	private String dialogue;
	private int importance;
	private int day;

	public PlanData(String name, String dialogue, int importance, int day) {
		this.name = name;
		this.dialogue = dialogue;
		this.importance = importance;
		this.day = day;
	}

	public String returnDialogue() {
		return this.dialogue;
	}

	public String returnName() {
		return this.name;
	}

	public int returnImportance() {
		return this.importance;
	}

	public int returnDay() {
		return this.day;
	}
}
