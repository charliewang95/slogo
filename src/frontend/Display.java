package frontend;

import java.util.Collection;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;

import backend.Interpreter;
import frontend.bottom.Console;
import frontend.bottom.History;
import frontend.center.TurtleLand;
import frontend.left.ToolBox;
import frontend.right.Variable;
import javafx.animation.KeyFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Playground;

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
	private History myHistory;
	private ToolBox myTool;
	private Variable myVariable;
	private TurtleLand myTurtleLand;
	private Stage myStage;
	private Group myRoot;
	private Scene myScene;

	private double myWidth;
	private double myHeight;
	private ResourceBundle myResources;

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
		// KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e
		// -> step(SECOND_DELAY));
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

	public History getHistory() {
		return myHistory;
	}

	public ToolBox getTool() {
		return myTool;
	}

	public TurtleLand getTurtleLand() {
		return myTurtleLand;
	}
}
