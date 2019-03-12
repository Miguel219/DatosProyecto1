package application;

import java.util.LinkedHashMap;

public class Function {

	private String name;
	private LinkedHashMap<String, String> params;
	private String body;

	/**
	 * Creador sin parametros
	 */
	public Function() {
		// TODO Auto-generated constructor stub
		name = "";
		params = new LinkedHashMap<>();
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
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
}
