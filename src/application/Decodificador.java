package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class Decodificador {

	private ArrayList<Function> functions;
	private ArrayList<String> text;
	private ArrayList<String> result;
	private int i;
	
	public Decodificador() {
		// TODO Auto-generated constructor stub
		functions = new ArrayList<>();
		text = new ArrayList<String>();
		result = new ArrayList<String>();
	}
	
	public ArrayList<String> add(String line) {
		//el resultado que se devuelve a la vista
		ArrayList<String> result = new ArrayList<>();
		//Se hace un arreglo de strings
		String[] textList = line.split(" ");
		//Se recorre la linea del archivo para guardar todas las operaciones
		for (i = 0; i < textList.length; i++) {
			String value = textList[i];
			if(!value.equals("")) {
				while((value.contains("("))&&(value.length()>1)) {
					if((value.contains("("))&&(value.length()>1)) {
						if(value.indexOf("(")==0) {
							text.add(value.substring(0, 1));
							value = value.substring(1);		
						}else {
							text.add(value.substring(0,value.indexOf("(")));
							value = value.substring(value.indexOf("("));		
						}
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
		//Se lee el archivo ya ordenado desde el principio		
		for (i = 0; i < text.size(); i++) {
			String value = text.get(i);
			System.out.println(value);
			if(value.equals("DEFUN")) {
				createFun();
			}else if((value.equals("="))||(value.equals("EQUAL"))||(value.equals(">"))||(value.equals("<"))||(value.equals("ATOM"))) {
				result.add(executeComp());
			}else if(value.equals("COND")) {
				result.add(executeCond());
			}else {
				boolean saved = false;
				for (int j = 0; j < functions.size(); j++) {
					String functionName = functions.get(j).getName();
					if(value.equals(functionName)) {
						saved = true;
						result.add(executeFun(j));
					}
				}
				//Si no es una llamada a una funcion es un numero entero
				if(!saved) {
					if((!value.equals(")"))&&(!value.equals("("))){
						result.add(value);
					}
				}
			}
			//Falta completar que ejecute funciones, operaciones aritmeticas y constantes
		}
		
		return result;
		
	}

	public void createFun() {
		Function function = new Function();
		int indicator = 1;
		int j = i;
		function.setPosition(j);
		Boolean bodyStarted = false;
		ArrayList<String> body = new ArrayList<>();
		//Se evalua si ya se acabo el cuerpo de la funcion
		while(indicator != 0) {
			j = j + 1;
			String valueInFunction = text.get(j);
			
			if(j == i+1) {
				function.setName(valueInFunction);
			}else if(j == i+3) {
				do {
					function.setParamName(valueInFunction);
					j = j + 1;
					valueInFunction = text.get(j);
				}while(!valueInFunction.equals(")"));
				bodyStarted = true;
			}
			
			//Se lleva el conteo de "(" y ")" en la funcion para saber cuando se termina
			if(valueInFunction.equals("(")) {
				indicator = indicator + 1;
			}else if(valueInFunction.equals(")")) {
				indicator = indicator - 1;
			}
			
			//Se agrega el cuerpo de la funcion
			if((bodyStarted)&&(indicator!=0)) {
				body.add(valueInFunction);
			}			
		}
		i = j;
		body.remove(0);
		function.setBody(body);
		functions.add(function);
	}
	
	public String executeFun(int j) {
		//Se inicializa el result de la funcion
		String result = "";
		//Se busca la funcion en el arraylist de la funcion
		Function currentFun = functions.get(j);
		//Se obtiene el cuerpo de la funcion
		ArrayList<String> body = currentFun.getBody();
		//Se buscan los parametros de la funcion y se setean los valores
		int indicator = 1;
		int contadorParametros = 0;
		j = i;
		while(indicator != 0) {
			j = j + 1;
			String valueInFunction = text.get(j);
			if(valueInFunction.equals("(")) {
				indicator = indicator + 1;
			}else if(valueInFunction.equals(")")) {
				indicator = indicator - 1;
			}else {
				Set<Map.Entry<String, String>> mapSet = currentFun.getParams().entrySet();
		        Map.Entry<String, String> param = (Map.Entry<String, String>) mapSet.toArray()[contadorParametros];
		        //Se setea el valor del parametro
		        param.setValue(valueInFunction);
		        contadorParametros = contadorParametros + 1;
			}
		}
		i = j;
		//Se recorre el cuerpo de la funcion para setear los valores de los parametros
		for (int k = 0; k < body.size(); k++) {
			String valueInBody = body.get(k);
			if(currentFun.getParams().containsKey(valueInBody)) {
				body.set(k,currentFun.getParams().get(valueInBody));
			};
		}
		//Se recorre el cuerpo de la funcion para ejecutarla
		for (int k = 0; k < body.size(); k++) {
			String valueInBody = body.get(k);
			if(((!valueInBody.equals(")"))||(!valueInBody.equals("(")))&&(result.equals(""))) {
				if((valueInBody.equals("="))||(valueInBody.equals("EQUAL"))||(valueInBody.equals(">"))||(valueInBody.equals("<"))||(valueInBody.equals("ATOM"))) {
					int indice = i;
					i = currentFun.getPosition();
					result = executeComp();
					i = indice;
				}else if(valueInBody.equals("COND")) {
					int indice = i;
					i = currentFun.getPosition();
					result = executeCond();
					i = indice;
				}else {
					boolean saved = false;
					for (int l = 0; l < functions.size(); l++) {
						String functionName = functions.get(l).getName();
						if(valueInBody.equals(functionName)) {
							saved = true;
							int indice = i;
							i = currentFun.getPosition();
							result = executeFun(j);
							i = indice;
						}
					}
					if(!saved) {
						result = valueInBody;
					}
				}
			}
		}
		
		
		return result;
	}
	
	public String executeComp() {
		String resultComp = "NIL";
		int indicator = 1;
		int j = i;
		String valueInCondition = text.get(j);
		while(valueInCondition.equals("(")) {
			j = j + 1;
			valueInCondition = text.get(j);
		};
		if(valueInCondition.equals("=")) {
			if(text.get(j+1).equals(text.get(j+2))) {
				resultComp = "T";
			}
		}else if(valueInCondition.equals("EQUAL")) {
			if(text.get(j+1).equals(text.get(j+2))) {
				resultComp = "T";
			}
		}else if(valueInCondition.equals(">")) {
			if(Integer.parseInt(text.get(j+1)) > Integer.parseInt(text.get(j+2))) {
				resultComp = "T";
			}
		}else if(valueInCondition.equals("<")) {
			if(Integer.parseInt(text.get(j+1)) < Integer.parseInt(text.get(j+2))) {
				resultComp = "T";
			}
		}else if(valueInCondition.equals("ATOM")) {
			do {
				j = j + 1;
				valueInCondition = text.get(j);
			}while(valueInCondition.equals("("));
			if(text.get(j+1).equals(")")) {
				resultComp = "T";
			}
		}
		j = i;
		while(indicator != 0) {
			j = j + 1;
			valueInCondition = text.get(j);
			if(valueInCondition.equals("(")) {
				indicator = indicator + 1;
			}else if (valueInCondition.equals(")")) {
				indicator = indicator - 1;
			}
		}
		i = j;
		return resultComp;
	}
	
	public String executeCond() {
		String result = "";
		int indicator = 1;
		int j = i;
		String valueInCondition = text.get(j);
		while(indicator != 0) {
			while((valueInCondition.equals(")"))||(valueInCondition.equals("("))){
				j = j + 1;
				valueInCondition = text.get(j);
				if(valueInCondition.equals("(")) {
					indicator = indicator + 1;
				}else if(valueInCondition.equals(")")) {
					indicator = indicator - 1;
				}
			};
			if(result.equals("")) {
				i = j;
				String resultBool = executeComp();
				j = i;
				if(resultBool.equals("T")) {
					valueInCondition = text.get(j);
					while((valueInCondition.equals(")"))||(valueInCondition.equals("("))) {
						j = j + 1;
						valueInCondition = text.get(j);
						if(valueInCondition.equals("(")) {
							indicator = indicator + 1;
						}else if(valueInCondition.equals(")")) {
							indicator = indicator - 1;
						}
					};
					if((valueInCondition.equals("="))||(valueInCondition.equals("EQUAL"))||(valueInCondition.equals(">"))||(valueInCondition.equals("<"))||(valueInCondition.equals("ATOM"))) {
						i = j;
						result = executeComp();
						j = i;
					}else if(valueInCondition.equals("COND")) {
						i = j;
						result = executeCond();
						j = i;
					}else {
						boolean saved = false;
						for (int k = 0; k < functions.size(); k++) {
							String functionName = functions.get(k).getName();
							if(valueInCondition.equals(functionName)) {
								saved = true;
								i = j;
								result = executeFun(k);
								j = i;
							}
						}
						//Si no es una llamada a una funcion es un numero entero
						if(!saved) {
							result = valueInCondition;
						}
					}
				}else if(resultBool.equals("false")) {
					//Repite el ciclo hasta llegar a la siguiente instruccion
					while(indicator != 1) {
						j = j + 1;
						valueInCondition = text.get(j);
						if(valueInCondition.equals("(")) {
							indicator = indicator + 1;
						}else if(valueInCondition.equals(")")) {
							indicator = indicator - 1;
						}
					}
				}
			}
		}
		return result;
	}
	
	public Integer executeOperation() {
		
		return i;
	}
	
}
