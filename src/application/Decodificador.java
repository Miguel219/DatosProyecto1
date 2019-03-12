package application;

import java.util.ArrayList;
import java.util.Arrays;

public class Decodificador {

	private ArrayList<Function> functions;
	private ArrayList<String> text;
	
	public Decodificador() {
		// TODO Auto-generated constructor stub
		functions = new ArrayList<>();
		text = new ArrayList<String>();
	}
	
	public void add(String line) {
		//el resultado que se devuelve a la vista
		ArrayList<String> result = new ArrayList<>();
		//Se hace un arreglo de strings
		String[] textList = line.split(" ");
		//Se recorre la linea del archivo para guardar todas las operaciones
		for (int i = 0; i < textList.length; i++) {
			String value = textList[i];
			System.out.println(value);
			if(!value.equals("")) {
				while((value.contains("("))&&(value.length()>1)) {
					if((value.contains("("))&&(value.length()>1)) {
						text.add(value.substring(0, 1));
						value = value.substring(1);					
					}
				}
				if((value.contains(")"))&&(value.length()>1)) {
					text.add(value.substring(0,value.indexOf(")")));
				}else {
					text.add(value);
				}
				while((value.contains(")"))&&(value.length()>1)) {
					if((value.contains(")"))&&(value.length()>1)) {
						text.add(value.substring(value.length()-1));
						value = value.substring(0, value.length()-1);
					}
				}
				
			}
		}
		
		for (int i = 0; i < text.size(); i++) {
			String value = text.get(i);
			if(value.equals("DEFUN")) {
				createFun(i);
			}
		}
	}

	public void createFun(int i) {
		Function function = new Function();
		int indicator = 1;
		int j = i;
		Boolean bodyStarted = false;
		String body = "";
		//Se evalua si ya se acabo el cuerpo de la funcion
		while(indicator != 0) {
			j = j + 1;
			String valueInFunction = text.get(j);
			
			if(j == i+1) {
				function.setName(valueInFunction);
			}else if(j == i+2) {
				function.setParamName(valueInFunction);
			}else if(valueInFunction.equals("(")) {
				bodyStarted = true;
			}
			
			//Se lleva el conteo de "(" y ")" en la funcion para saber cuando se termina
			if(valueInFunction.contains("(")) {
				indicator = indicator + 1;
			}else if(valueInFunction.contains(")")) {
				indicator = indicator - 1;
			}
			
			//Se agrega el cuerpo de la funcion
			if((bodyStarted)&&(indicator!=0)) {
				body = body + valueInFunction;
			}
			
		}
		function.setBody(body);
		functions.add(function);
	}
	
	public String executeCond(int i) {
		int indicator = 1;
		int j = i;
		String cond = "";
		//Se evalua si ya se acabo el cuerpo de la funcion
		while(indicator != 0) {
			j = j + 1;
			String valueInCondition = text.get(j);
			
			
			//Se lleva el conteo de "(" y ")" en la funcion para saber cuando se termina
			if(valueInCondition.contains("(")) {
				indicator = indicator + 1;
			}else if(valueInCondition.contains(")")) {
				indicator = indicator - 1;
			}
			
			//Se guardan todas las condiciones
			if(indicator==2) {
				if(valueInCondition.contains("=")) {
					if(text.get(j+1).equals(text.get(j+2))) {
						return text.get(j+3);
					}
				}
			}
		}
		return null;
	}
	
	public Integer executeOperation(int i) {
		
		return i;
	}
	
}
