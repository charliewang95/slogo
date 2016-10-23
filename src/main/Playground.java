package main;

import backend.Interpreter;
import frontend.Display;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Playground {
	private Display myDisplay;
	private Stage myStage;
	private Interpreter myInterpreter;
	
	public Playground(Stage s) {
		myStage = s;
		myInterpreter = new Interpreter(this);
	}
	
	public void init() {
		myDisplay = new Display(myStage);
		myDisplay.init(myInterpreter);
	}
	
	public void clearScreen() {
		myDisplay.getTurtleLand().reset();
	}
	
	public String getCommandError() {
		myInterpreter.getCommandError();
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
