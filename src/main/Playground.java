package main;

import backend.Interpreter;
import frontend.Display;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Playground {
	private Display myDisplay;
	private Stage myStage;
	
	public Playground(Stage s) {
		myStage = s;
	}
	
	public void init() {
		myDisplay = new Display(myStage);
		myDisplay.init();
	}
	
	public void interpretInput(String input){
		
		try {
			Interpreter.class.newInstance().interpretString(input);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
