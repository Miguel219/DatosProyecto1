package application;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Function {

	private String name;
	private int position;
	private LinkedHashMap<String, String> params;
	private ArrayList<String> body;

	/**
	 * Creador sin parametros
	 */
	public Function() {
		// TODO Auto-generated constructor stub
		name = "";
		params = new LinkedHashMap<>();
		body = new ArrayList<>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the params
	 */
	public LinkedHashMap<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParamName(String paramName) {
		params.put(paramName, "");
	}

	/**
	 * @return the body
	 */
	public ArrayList<String> getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(ArrayList<String> body) {
		this.body = body;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	
}
