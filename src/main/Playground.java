package main;

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
	
	
}
