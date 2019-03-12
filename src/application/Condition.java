package application;

import java.util.ArrayList;

public class Condition {

	private ArrayList<String> conditions;
	
	/**
	 * Constructor de la clase 
	 */
	public Condition() {
		// TODO Auto-generated constructor stub
		conditions = new ArrayList<>();
	}

	/**
	 * @return the conditions
	 */
	public ArrayList<String> getConditions() {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(String condition) {
		conditions.add(condition);
	}

}
