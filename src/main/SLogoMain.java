package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class SLogoMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Playground p = new Playground(primaryStage);
		p.init();
	}

}
