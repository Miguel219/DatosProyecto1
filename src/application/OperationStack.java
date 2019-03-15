package application;

/**
 * Silvio Orozco 18282
 * Jose Castaneda 18161
 * Roberto Castillo 185546
 * 
 * Proyecto 1 	15/3/2019
 */
import java.util.Vector;

public class OperationStack implements Stack<String>{
	
	/**
	 * Vector que almacena los operandos y operaciones
	 */
	private Vector<String> vector;
	
	/**
	 * Constructor
	 */
	public OperationStack() {
		// TODO Auto-generated constructor stub
		vector = new Vector<String>();
	}

	@Override
	public void push(String item) {
		// TODO Auto-generated method stub
		vector.add(item);
	}

	@Override
	public String pop() {
		// TODO Auto-generated method stub
		if(!vector.isEmpty()) {
			String lastElement = vector.lastElement();
			vector.remove(vector.size()-1);
			return lastElement;
		}else {			
			return null;
		}
	}

	@Override
	public String peek() {
		// TODO Auto-generated method stub
		if(!vector.isEmpty()) {
			String lastElement = vector.lastElement();
			return lastElement;
		}else {			
			return null;
		}
	}

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		if(!vector.isEmpty()) {
			return true;
		}else {			
			return false;
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return vector.size();
	}

}
