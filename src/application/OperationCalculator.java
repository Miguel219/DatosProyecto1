package application;

/**
 * Silvio Orozco 18282
 * Jose Castaneda 18161
 * Roberto Castillo 185546
 * 
 * Proyecto 1 	15/3/2019
 */
public class OperationCalculator implements Calculator {
	
	
	public OperationCalculator() {}
	
	@Override
	public String Calculate(String numA, String numB, String op) {
		int ans = 0;
		int num1;
		int num2;
		String ansString;
		try {
			num1 = Integer.parseInt(numA);
					
		}
		catch(Exception e){
			if(numA.equals("Error division por cero")) {
				return ("Error division por cero");
			}else {
				return ("Error no se puede convertir String a Int");
			}
		}
		try {
			num2 = Integer.parseInt(numB);
					
		}
		catch(Exception e){
			if(numB.equals("Error division por cero")) {
				return ("Error division por cero");
			}else {
				return ("Error no se puede convertir String a Int");
			}
		}
		
		if (op.equals("+")) {
			ans = num1 + num2;	
		}
		
		
		if (op.equals("-")) {
			ans = num1 - num2;
		}
		
		
		if (op.equals("*")) {
			ans = num1 * num2;
		}
		
		
		if (op.equals("/")) {
		
			if (num2 != 0) {
				float an = (num1 / num2);
				ans = (int)an;
			} else {
				ansString = ("Error division por cero");
			}
			
		}
		ansString = Integer.toString(ans);
		return ansString;
	}
}