package frontend.center;

import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TurtleLand {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Color defaultGround = Color.LIGHTGREEN;
	private Pane myPane;
	private Canvas myCanvas, myBackground;
	private int myWidth;
	private int myHeight;
	private ResourceBundle myResources;
	private GraphicsContext gcb, gcc;
	private double centerX;
	private double centerY;

	public TurtleLand() {
		myPane = new Pane();
		
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myWidth = Integer.parseInt(myResources.getString("CanvasWidth"));
		myHeight = Integer.parseInt(myResources.getString("CanvasHeight"));
		myPane.setPrefSize(myWidth, myHeight);
		
		centerX = myWidth / 2.0;
		centerY = myHeight / 2.0;
		
		myCanvas = new Canvas(myWidth, myHeight);
		myBackground = new Canvas(myWidth, myHeight);
		
		gcb = myBackground.getGraphicsContext2D();
		gcc = myCanvas.getGraphicsContext2D();
		
		gcb.setFill(defaultGround);
		gcb.fillRect(0, 0, myWidth, myHeight);
		
		gcc.setFill(Color.RED);
		gcc.fillOval(centerX, centerY, 20, 20);
		
		myPane.getChildren().add(myBackground);
		myPane.getChildren().add(myCanvas);
	}

	public Pane getLand() {
		return myPane;
	}

	public void changeBackground(Color c) {
		gcb.setFill(c);
		gcb.fillRect(0, 0, myWidth, myHeight);
	}
}
