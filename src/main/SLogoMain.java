package main;

import javafx.application.Application;
import javafx.stage.Stage;
import user_interface.StartScreen;

public class SLogoMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new StartScreen(primaryStage);
	}

}
