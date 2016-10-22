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
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Color defaultGround = Color.LIGHTGREEN;
	private Pane myPane;
	private Canvas myCanvas, myBackground;
	private TurtleMascot myTurtle;
	private int myWidth;
	private int myHeight;
	private ResourceBundle myResources;
	private GraphicsContext gcb, gcc;
	private double centerX;
	private double centerY;
	private CoordinateConverter converter;

	public TurtleLand() {
		myPane = new Pane();
		
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myWidth = Integer.parseInt(myResources.getString("CanvasWidth"));
		myHeight = Integer.parseInt(myResources.getString("CanvasHeight"));
		myPane.setPrefSize(myWidth, myHeight);
		
		converter = new CoordinateConverter(myWidth,myHeight,TurtleMascot.WIDTH,TurtleMascot.HEIGHT);
		
		centerX = myWidth / 2.0;
		centerY = myHeight / 2.0;
		
		myCanvas = new Canvas(myWidth, myHeight);
		myBackground = new Canvas(myWidth, myHeight);
		
		gcb = myBackground.getGraphicsContext2D();
		gcc = myCanvas.getGraphicsContext2D();
		
		gcb.setFill(defaultGround);
		gcb.fillRect(0, 0, myWidth, myHeight);
		
		gcc.setFill(Color.RED);
		gcc.fillOval(centerX-10, centerY-10, 20, 20);
		gcc.setLineWidth(2);
                gcc.setFill(Color.BLUE);
                gcc.strokeRect(centerX-20, centerY-25, 40, 50); //encloses the turtle in the center
		
		myTurtle = new TurtleMascot(converter);
		myTurtle.setX(0);
		myTurtle.setY(0);
		
		myPane.getChildren().add(myBackground);
		myPane.getChildren().add(myCanvas);
		myPane.getChildren().add(myTurtle.getImage());
	}

	public Pane getLand() {
		return myPane;
	}

	public void changeBackground(Color c) {
		gcb.setFill(c);
		gcb.fillRect(0, 0, myWidth, myHeight);
	}
}
