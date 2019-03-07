package application;

public class Decodificador {

	public Decodificador() {
		// TODO Auto-generated constructor stub
	}
	
	public void add(String line) {
		//Se hace un arreglo de strings
		String[] text = line.split(" ");
		//Se recorre la linea del archivo txt
		for (int i = 0; i < text.length; i++) {
			String value = text[i];
			if (value.equals("(defun")){
				
			}
		}
	}

}
