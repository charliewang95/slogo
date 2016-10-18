package frontend;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author Charlie Wang
 */
public class Display {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";

	private BorderPane myBorderPane;
	private Console myConsole;
	private History myHistory;
	private ToolBox myTool;
	private Canvas myCanvas;
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
	}

	public void init() {
		myBorderPane = new BorderPane();
		myBorderPane.setPrefSize(myWidth, myHeight);
		myBorderPane.setStyle("-fx-background-color: linear-gradient(from 15% 15% to 85% 85%, #42E5EA, #4FEFA4)");
		
		// set up console
		myConsole = new Console(this);
		// BorderPane.setAlignment(myConsole.getConsole(), Pos.CENTER_RIGHT);
		myBorderPane.setBottom(myConsole.getConsole());

		// set up command history
		//myHistory = new History(this);
		//myBorderPane.setRight(myHistory.getHistory());
		// BorderPane.setAlignment(myHistory.getHistory(), Pos.TOP_LEFT);

		// set up toolbar
		myTool = new ToolBox(this);
		myBorderPane.setLeft(myTool.getTool());

		// set up canvas
		myCanvas = new Canvas();
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		
		myBorderPane.setCenter(myCanvas);
		gc.fillRect(myBorderPane.getCenter().getLayoutX(), myBorderPane.getCenter().getLayoutY(),
				myBorderPane.getCenter().getScaleX(), myBorderPane.getCenter().getScaleY());
		gc.setFill(Color.ORANGE);

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
}
