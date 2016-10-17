package frontend;

import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Charlie Wang
 *
 */
public class Display {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	
	private BorderPane bp;
	private Console myConsole;
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
		
		//set up console
		myConsole = new Console();
		bp.setLeft(myConsole.getConsole());
		
		display();
	}
	
	private void display() {
		myRoot.getChildren().add(bp);
		myStage.setScene(myScene);
		myStage.setTitle(myResources.getString("Title"));
		myStage.show();
	}
	
	public double getWidth() {
		return myWidth;
	}
	
	public double getHeight() {
		return myHeight;
	}
}
