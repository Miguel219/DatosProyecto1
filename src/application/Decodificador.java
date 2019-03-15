package application;

/**
 * Silvio Orozco 18282
 * Jose Castaneda 18161
 * Roberto Castillo 185546
 * 
 * Proyecto 1 	15/3/2019
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Decodificador {

	private ArrayList<Function> functions;
	private ArrayList<String> text;
	private ArrayList<String> result;
	private ArrayList<String> operations;
	private int i;
	
	public Decodificador() {
		// TODO Auto-generated constructor stub
		functions = new ArrayList<>();
		text = new ArrayList<String>();
		result = new ArrayList<String>();
	}
	
	/**
	 * Esta funcion lee todo el codigo en lisp
	 * @param line el codigo en lisp
	 * @return result = los resultados al ejecutar el codigo
	 */
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
			}else if(value.equals("+")|| value.equals("*") ||value.equals("/")||value.equals("-")) {
				result.add(executeOperation());
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

	/**
	 * Esta funcion crea un objeto de tipo Function() 
	 */
	public void createFun() {
		Function function = new Function();
		int indicator = 1;
		int j = i;
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
				function.setPosition(j+1);
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
	
	/**
	 * Esta funcion ejecuta la funcion al ser llamada
	 * @param j = el indice de la funcion llamada
	 * @return result = el resultado que se produce al ejecutar la funcion 
	 */
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
			String paramValue = "";
			if((valueInFunction.equals(")"))||(valueInFunction.equals("("))){
				if(valueInFunction.equals("(")) {
					indicator = indicator + 1;
				}else if(valueInFunction.equals(")")) {
					indicator = indicator - 1;
				}
			}else {
				if((valueInFunction.equals("="))||(valueInFunction.equals("EQUAL"))||(valueInFunction.equals(">"))||(valueInFunction.equals("<"))||(valueInFunction.equals("ATOM"))) {
					i = j;
					paramValue = executeComp();
					j = i;
				}else if(valueInFunction.equals("COND")) {
					i = j;
					paramValue = executeCond();
					j = i;
				}else if(valueInFunction.equals("+")|| valueInFunction.equals("*") ||valueInFunction.equals("/")||valueInFunction.equals("-")) {
					i = j;
					paramValue = executeOperation();
					j = i;
				}else {
					boolean saved = false;
					for (int l = 0; l < functions.size(); l++) {
						String functionName = functions.get(l).getName();
						if(valueInFunction.equals(functionName)) {
							saved = true;
							int indice = i;
							i = currentFun.getPosition();
							paramValue = executeFun(j);
							i = indice - 1;
							while(indicator != 0) {
								i = i + 1;
								valueInFunction = text.get(i);
								if(valueInFunction.equals("(")) {
									indicator = indicator + 1;
								}else if (valueInFunction.equals(")")) {
									indicator = indicator - 1;
								}
							}
						}
					}
					//Si no es una llamada a una funcion es un numero entero
					if(!saved) {
						paramValue = valueInFunction;
					}
				}
				Set<Map.Entry<String, String>> mapSet = currentFun.getParams().entrySet();
		        Map.Entry<String, String> param = (Map.Entry<String, String>) mapSet.toArray()[contadorParametros];
		        //Se setea el valor del parametro
		        param.setValue(paramValue);
		        contadorParametros = contadorParametros + 1;
			}
		}
		i = j;
		//Se recorre el cuerpo de la funcion para ejecutarla
		for (int k = 0; k < body.size(); k++) {
			String valueInBody = body.get(k);
			if(((!valueInBody.equals(")"))&&(!valueInBody.equals("(")))&&(result.equals(""))) {
				if((valueInBody.equals("="))||(valueInBody.equals("EQUAL"))||(valueInBody.equals(">"))||(valueInBody.equals("<"))||(valueInBody.equals("ATOM"))) {
					int indice = i;
					i = currentFun.getPosition() - 1;
					do {
						i = i +1;
						valueInBody = text.get(i);
					}while((!valueInBody.equals("="))&&(!valueInBody.equals("EQUAL"))&&(!valueInBody.equals(">"))&&(!valueInBody.equals("<"))&&(!valueInBody.equals("ATOM")));
					result = executeComp();
					i = indice;
				}else if(valueInBody.equals("COND")) {
					int indice = i;
					i = currentFun.getPosition() - 1;
					do {
						i = i +1;
						valueInBody = text.get(i);
					}while(!valueInBody.equals("COND"));
					result = executeCond();
					i = indice;
				}else if(valueInBody.equals("+")|| valueInBody.equals("*") ||valueInBody.equals("/")||valueInBody.equals("-")) {
					int indice = i;
					i = currentFun.getPosition() - 1;
					do {
						i = i +1;
						valueInBody = text.get(i);
					}while(!valueInBody.equals("+")&& !valueInBody.equals("*") &&!valueInBody.equals("/")&&!valueInBody.equals("-"));
					result = executeOperation();
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
	
	/**
	 * funcion que ejecuta una comparacion
	 * @return result = el resultado al ejecutar una comparacion (T o NIL) 
	 */
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
			String val1 = text.get(j+1);
			String val2 = text.get(j+2);
			for (int k = 0; k < functions.size(); k++) {
				HashMap<String, String> functionParams = functions.get(k).getParams();
				if(functionParams.containsKey(val1)) {	
					val1 = functionParams.get(val1);
				}else if(functionParams.containsKey(val2)) {	
					val1 = functionParams.get(val2);
				}
			}
			if(val1.equals(val2)) {
				resultComp = "T";
			}
		}else if(valueInCondition.equals("EQUAL")) {
			String val1 = text.get(j+1);
			String val2 = text.get(j+2);
			for (int k = 0; k < functions.size(); k++) {
				HashMap<String, String> functionParams = functions.get(k).getParams();
				if(functionParams.containsKey(val1)) {	
					val1 = functionParams.get(val1);
				}else if(functionParams.containsKey(val2)) {	
					val1 = functionParams.get(val2);
				}
			}
			if(val1.equals(val2)) {
				resultComp = "T";
			}
		}else if(valueInCondition.equals(">")) {
			String val1 = text.get(j+1);
			String val2 = text.get(j+2);
			for (int k = 0; k < functions.size(); k++) {
				HashMap<String, String> functionParams = functions.get(k).getParams();
				if(functionParams.containsKey(val1)) {	
					val1 = functionParams.get(val1);
				}else if(functionParams.containsKey(val2)) {	
					val1 = functionParams.get(val2);
				}
			}
			if(Integer.parseInt(val1) > Integer.parseInt(val2)) {
				resultComp = "T";
			}
		}else if(valueInCondition.equals("<")) {
			String val1 = text.get(j+1);
			String val2 = text.get(j+2);
			for (int k = 0; k < functions.size(); k++) {
				HashMap<String, String> functionParams = functions.get(k).getParams();
				if(functionParams.containsKey(val1)) {	
					val1 = functionParams.get(val1);
				}else if(functionParams.containsKey(val2)) {	
					val1 = functionParams.get(val2);
				}
			}
			if(Integer.parseInt(val1) < Integer.parseInt(val2)) {
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
	
	/**
	 * Funcion que ejecuta una condicion
	 * @return result = el resultado al ejecutar una condicion
	 */
	public String executeCond() {
		String result = "";
		int indicator = 1;
		int j = i;
		String valueInCondition = text.get(j);
		while(!valueInCondition.equals("COND")){
			j = j + 1;
			valueInCondition = text.get(j);
		};
		while((indicator != 0)&&(result.equals(""))) {
			valueInCondition = text.get(j+1);
			while((!valueInCondition.equals("="))&&(!valueInCondition.equals("EQUAL"))&&(!valueInCondition.equals(">"))&&(!valueInCondition.equals("<"))&&(!valueInCondition.equals("ATOM"))){
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
					}else if(valueInCondition.equals("+")|| valueInCondition.equals("*") ||valueInCondition.equals("/")||valueInCondition.equals("-")) {
						i = j;
						result = executeOperation();
						j = i;
					}
					else {
						boolean saved = false;
						for (int k = 0; k < functions.size(); k++) {
							String functionName = functions.get(k).getName();
							HashMap<String, String> functionParams = functions.get(k).getParams();
							if(valueInCondition.equals(functionName)) {
								saved = true;
								i = j;
								result = executeFun(k);
								j = i;
							}else if(functionParams.containsKey(valueInCondition)) {
								saved = true;	
								result = functionParams.get(valueInCondition);
							}
						}
						//Si no es una llamada a una funcion es un numero entero
						if(!saved) {
							result = valueInCondition;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Funcion que ejecuta una operacion (+ o - o * o /)
	 * @return result = resultado al hacer una de las siguientes operaciones (+ o - o * o /)
	 */
	public String executeOperation() {
		OperationCalculator MyCalculator = new OperationCalculator();
		OperationStack OperatorStack = new OperationStack();
		String result = "";
		int indicator = 1;
		int j = i;
		String valueInOperation = text.get(j);
		if(valueInOperation.equals("+")|| valueInOperation.equals("*") ||valueInOperation.equals("/")||valueInOperation.equals("-")) {
			OperatorStack.push(valueInOperation);
		}
		while((indicator != 0)||(OperatorStack.size()!=3)) {
			j = j + 1;
			valueInOperation = text.get(j);
				
			if(valueInOperation.equals("(")) {
				indicator = indicator + 1;
			}else if(valueInOperation.equals(")")) {
				indicator = indicator - 1;
			}else if(valueInOperation.equals("COND")) {
				i = j;
				if(OperatorStack.size()!=3) {
					OperatorStack.push(executeCond());
				}
				indicator = indicator - 1;
				j = i;
			}else if(valueInOperation.equals("+")|| valueInOperation.equals("*") ||valueInOperation.equals("/")||valueInOperation.equals("-")) {
				i = j;
				if((OperatorStack.size()!=3)) {
					OperatorStack.push(executeOperation());
				}
				indicator = indicator - 1;
				j = i;
			}
			else {
				boolean saved = false;
				for (int k = 0; k < functions.size(); k++) {
					String functionName = functions.get(k).getName();
					HashMap<String, String> functionParams = functions.get(k).getParams();
					if(valueInOperation.equals(functionName)) {
						saved = true;
						i = j;
						if(OperatorStack.size()!=3){
							OperatorStack.push(executeFun(k));
						}
						j = i;
					}else if(functionParams.containsKey(valueInOperation)) {
						saved = true;
						if(OperatorStack.size()!=3){
							OperatorStack.push(functionParams.get(valueInOperation));
						}
					}
				}
				//Si no es una llamada a una funcion es un numero entero
				if(!saved) {
					if(OperatorStack.size()!=3) {
						OperatorStack.push(valueInOperation);
					}
				}
			}
		}
		i = j;
		String num2 = OperatorStack.pop();
		String num1 = OperatorStack.pop();
		String signOperator= OperatorStack.pop();
		result = MyCalculator.Calculate(num1, num2, signOperator);
		OperatorStack.push(result);
		return result;
	}
	
}
