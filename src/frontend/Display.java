package frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

import backend.Interpreter;
import frontend.bottom.Console;
import frontend.center.TurtleLand;
import frontend.left.ToolBox;
import frontend.right.Variable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Charlie Wang
 */
public class Display {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private static final int MILLISECOND_DELAY = 1000;
	private static final int SECOND_DELAY = 1;
	private Interpreter myInterpreter;
	private BorderPane myBorderPane;
	private Console myConsole;
	private ToolBox myTool;
	private Variable myVariable;
	private TurtleLand myTurtleLand;
	private Stage myStage;
	private Group myRoot;
	private Scene myScene;

	private double myWidth;
	private double myHeight;
	private ResourceBundle myResources;
	private Scanner in;

	public Display(Stage s) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myStage = new Stage();
		myRoot = new Group();
		myWidth = Integer.parseInt(myResources.getString("Width"));
		myHeight = Integer.parseInt(myResources.getString("Height"));
		myScene = new Scene(myRoot, myWidth, myHeight);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}

	private void handleKeyInput(KeyCode code) {
		if (code == KeyCode.TAB) {
			myTurtleLand.toggleParameters();
		}
	}

	public void init(Interpreter inter) {
		myInterpreter = inter;

		myBorderPane = new BorderPane();
		myBorderPane.setPrefSize(myWidth, myHeight);
		myBorderPane.setStyle("-fx-background-color: linear-gradient(from 5% 5% to 95% 95%, #C1D6F6, #76ACFE)");

		// set up console and history
		myConsole = new Console(this, myInterpreter);
		myBorderPane.setBottom(myConsole.getConsole());

		// set up toolbar
		myTool = new ToolBox(this);
		myBorderPane.setLeft(myTool.getTool());

		// set up variable and new commands screen
		myVariable = new Variable();
		myBorderPane.setRight(myVariable.getVariable());

		// set up canvas
		myTurtleLand = new TurtleLand(this);
		BorderPane.setMargin(myTurtleLand.getLand(), new Insets(20, 20, 20, 20));
		myBorderPane.setCenter(myTurtleLand.getLand());

		// put everything there on the board
		display();
	}

	private void display() {
		myRoot.getChildren().add(myBorderPane);
		myStage.setScene(myScene);
		myStage.setTitle(myResources.getString("Title"));
		myStage.show();
		myStage.setResizable(false);
	}

	public double getWidth() {
		return myWidth;
	}

	public double getHeight() {
		return myHeight;
	}

	public Console getConsole() {
		return myConsole;
	}

	public ToolBox getTool() {
		return myTool;
	}

	public TurtleLand getTurtleLand() {
		return myTurtleLand;
	}
	
	public void updateText() {
		myTurtleLand.updateText();
	}
	
	public void changeTurtle(String newName, File newImage) {
		myTurtleLand.changeTurtle(newName, newImage);
	}
	
	public void changeTurtle(String value) {
		myTurtleLand.changeTurtle(value);
	}
	
	public void setLanguage(String value) {
		myConsole.setLanguage(value);
	}
	
	public void interpretInput(String in) {
		myConsole.interpretInput(in);
	}
	
	public void printHistoryToFile() {
		myConsole.getHistory().printHistoryToFile();
	}
	
	public void printGround() {
		myTurtleLand.printGround();
	}
	
	public void setPenColor(Color color) {
		myTurtleLand.setPenColor(color);
	}
	
	public void changeBackground(Color c) {
		myTurtleLand.changeBackground(c);
	}
	
	public void changePenSize() {
		
	}
	
	public void saveDefaultConfig() {
		StringBuilder sb = new StringBuilder();
		sb.append(myTurtleLand.getBackgroundColor()+"\n");
		sb.append(myTurtleLand.getTurtle().getPenColor()+"\n");
		sb.append(myTurtleLand.getTurtle().getAnimal()+"\n");
		sb.append(myTool.getLanguage());
		
		File file = new File("data/output.txt");
		String toWrite = sb.toString();

		try (FileOutputStream fos = new FileOutputStream(file)) {
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] byteArray = toWrite.getBytes();
			fos.write(byteArray);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDefaultConfig(File file) throws FileNotFoundException {
		in = new Scanner(file);
		String bkcolor = in.next();
		myTurtleLand.changeBackground(bkcolor);
		String pencolor = in.next();	
		myTurtleLand.changeBackground(pencolor);
		String turtlepic = in.next();
		myTurtleLand.changeTurtle(turtlepic);
		String lang = in.next();
		myTool.setLanguage(lang);
		myConsole.setLanguage(lang);
	}
}
