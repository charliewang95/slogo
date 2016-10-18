package frontend;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Charlie Wang
 *
 */
public class Display {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";

	private BorderPane bp;
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
		bp = new BorderPane();
		bp.setPrefSize(myWidth, myHeight);
		bp.setStyle("-fx-background-color: linear-gradient(from 15% 15% to 85% 85%, #42E5EA, #4FEFA4)");
		// set up console
		myConsole = new Console(this);
		// BorderPane.setAlignment(myConsole.getConsole(), Pos.CENTER_RIGHT);
		bp.setTop(myConsole.getConsole());

		// set up command history
		myHistory = new History(this);
		bp.setRight(myHistory.getHistory());
		// BorderPane.setAlignment(myHistory.getHistory(), Pos.TOP_LEFT);

		// set up toolbar
		myTool = new ToolBox(this);
		bp.setLeft(myTool.getTool());

		// set up canvas
		//myCanvas = new Canvas(500, 500);
		//GraphicsContext gc = myCanvas.getGraphicsContext2D();
		//gc.setFill(Color.ORANGE);
		//gc.fill();
		// myCanvas.autosize();
		//bp.setBottom(myCanvas);

		display();
	}

	private void display() {
		myRoot.getChildren().add(bp);
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
