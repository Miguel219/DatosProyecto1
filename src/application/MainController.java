package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MainController {
	
	@FXML
	private TextField pathTextField;
	@FXML
	private Label result;
	@FXML	
	private FlowPane codeFlowPane;
	@FXML	
	private FlowPane resultFlowPane;
	
	/**
	 * Objeto de la clase que implementa Stack 
	 */
	private OperationStack stack;
	/**
	 * Objeto de la clase que decodifica el codigo Lisp 
	 */
	private Decodificador decodificador;
	/**
	 * objeto de la clase que implementa Calculator
	 */
	private OperationCalculator calculator;
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
			String code = "";
			int i = 0;
			while (s.hasNextLine()) {
				i = i +1;
				line = s.nextLine();
				code = code + " " + line; 
				Label label = new Label(i+".	"+line);
				Region p = new Region();
				p.setPrefSize(600.0, 4.0);
				Line linee = new Line(0, 0, 600, 0);
				Region p1 = new Region();
				p1.setPrefSize(600.0, 4.0);
				//Se agregan al FlowPane
				codeFlowPane.getChildren().add(label);
				codeFlowPane.getChildren().add(p);
				codeFlowPane.getChildren().add(linee);
				codeFlowPane.getChildren().add(p1);
			}

			//Se utiliza el objero decodificador para leer la linea
			ArrayList<String> result = decodificador.add(code);
			System.out.println(result.size());
			for (int j = 0; j < result.size(); j++) {
				String currentResult = result.get(j);
				
				Label label = new Label(currentResult);
				label.setTextFill(Color.WHITE);;
				Region p = new Region();
				p.setPrefSize(600.0, 4.0);
				Line linee = new Line(0, 0, 600, 0);
				linee.setStroke(Color.WHITE);
				Region p1 = new Region();
				p1.setPrefSize(600.0, 4.0);
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
		stack = new OperationStack();
		calculator = new OperationCalculator();
		decodificador = new Decodificador();
    }
	
}
