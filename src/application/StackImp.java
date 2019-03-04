package application;

import java.util.Vector;

public class StackImp implements Stack<Integer>{
	
	/**
	 * Vector que almacena los operandos y operaciones
	 */
	private Vector<Integer> vector;
	
	/**
	 * Constructor
	 */
	public StackImp() {
		// TODO Auto-generated constructor stub
		vector = new Vector<Integer>();
	}

	@Override
	public void push(Integer item) {
		// TODO Auto-generated method stub
		vector.add(item);
	}

	@Override
	public Integer pop() {
		// TODO Auto-generated method stub
		if(!vector.isEmpty()) {
			Integer lastElement = vector.lastElement();
			vector.remove(vector.size()-1);
			return lastElement;
		}else {			
			return null;
		}
	}

	@Override
	public Integer peek() {
		// TODO Auto-generated method stub
		if(!vector.isEmpty()) {
			Integer lastElement = vector.lastElement();
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
