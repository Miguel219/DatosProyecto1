package application;

import java.io.File;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

public class MainController {
	
	@FXML
	private TextField pathTextField;
	@FXML
	private Label result;
	@FXML	
	private FlowPane resultFlowPane;
	
	/**
	 * Objeto de la clase que implementa Stack 
	 */
	private StackImp stack;
	/**
	 * objeto de la clase que implementa Calculator
	 */
	private ImpCalculadora calculator;
	/**
	 * Es en donde se almacena la informacion leida en el archivo txt
	 */
	private Scanner s;
	
	/**
	 *	lee el archivo txt 
	 */
	public void read() {
		//Si ya se verifico y no hay error se lee el archivo
		if(verifyPath()) {
			//Se recorre todo el archivo
			String line = "";
			while (s.hasNextLine()) {
				line = s.nextLine()+ " ";
				//Se hace un arreglo de strings
				String[] text = line.split(" ");
				//Se recorre la linea del archivo txt
				for (int i = 0; i < text.length; i++) {
					String value = text[i];
					//Se guarda el valor en el stack
					try {
						//Se intenta convertir a int
						Integer intValue = Integer.parseInt(value);
						stack.push(intValue);
					} catch (Exception e) {
						// TODO: handle exception
						//Si no es int es un operando entonces ya se puede efectuar la operacion
						//Se sacan la operacion y los dos operandos
						String operacion = value;
						int operando1 = stack.pop();
						int operando2 = stack.pop();
						//Se hace la operacion
						int resultado = calculator.Calculate(operando1, operando2, operacion);
						//Se guarda el resultado en la primera posicion del stack
						stack.push(resultado);
					}
				}
				Label label = new Label("Resultado: "+stack.pop());
				Region p = new Region();
				p.setPrefSize(347.0, 4.0);
				Line linee = new Line(0, 0, 350, 0);
				Region p1 = new Region();
				p1.setPrefSize(347.0, 4.0);
				//Se agregan al FlowPane
				resultFlowPane.getChildren().add(label);
				resultFlowPane.getChildren().add(p);
				resultFlowPane.getChildren().add(linee);
				resultFlowPane.getChildren().add(p1);
			}
		}
	}
	
	/**
	 * Verifica que el path sea correcto 
	 * @return boolean = si se pudo abrir el path
	 */
	public boolean verifyPath() {
		try {
			//Se lee el archivo
			s = new Scanner(new File(pathTextField.getText()));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error en datos ingresado");
			alert.setContentText("Verifica la dirección del archivo ingresado sea correcta");
			alert.showAndWait();
			return false;
		}
	}
	
	@FXML
    public void initialize() {
		stack = new StackImp();
		calculator = new ImpCalculadora();
    }
	
}
