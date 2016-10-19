package frontend.center;

import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TurtleLand {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Color defaultGround = Color.LIGHTGREEN;
	private Canvas myCanvas;
	private int myWidth;
	private int myHeight;
	private ResourceBundle myResources;
	private GraphicsContext gc;
	private double centerX;
	private double centerY;

	public TurtleLand() {

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myWidth = Integer.parseInt(myResources.getString("CanvasWidth"));
		myHeight = Integer.parseInt(myResources.getString("CanvasHeight"));
		centerX = myWidth / 2.0;
		centerY = myHeight / 2.0;
		System.out.println(centerX + " " + centerY);
		
		myCanvas = new Canvas(myWidth, myHeight);
		gc = myCanvas.getGraphicsContext2D();
		gc.setFill(defaultGround);
		gc.fillRect(0, 0, myWidth, myHeight);
		
		gc.setFill(Color.RED);
		gc.fillRect(centerX - 10, centerY - 10, 20, 20);
		
	}

	public Canvas getCanvas() {
		return myCanvas;
	}

	public void changeBackground(Color c) {
		gc.setFill(c);
		gc.fillRect(0, 0, myWidth, myHeight);
	}
}
