package frontend.center;

import java.io.File;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TurtleLand {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Color defaultGround = Color.LIGHTGREEN;
	private Pane myPane;
	private Canvas myCanvas, myBackground;
	private TurtleMascot myTurtle;
	private ImageView myTurtleImage;
	private int myWidth;
	private int myHeight;
	private ResourceBundle myResources;
	private GraphicsContext gcb, gcc;
	private int centerX;
	private int centerY;
	private int fittedX;
	private int fittedY;

	public TurtleLand() {
		myPane = new Pane();
		
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myWidth = Integer.parseInt(myResources.getString("CanvasWidth"));
		myHeight = Integer.parseInt(myResources.getString("CanvasHeight"));
		myPane.setPrefSize(myWidth, myHeight);
		
		centerX = myWidth / 2;
		centerY = myHeight / 2;
		
		myCanvas = new Canvas(myWidth, myHeight);
		myBackground = new Canvas(myWidth, myHeight);
		
		gcb = myBackground.getGraphicsContext2D();
		gcc = myCanvas.getGraphicsContext2D();
		
		gcb.setFill(defaultGround);
		gcb.fillRect(0, 0, myWidth, myHeight);
		
		gcc.setFill(Color.RED);
		//gcc.fillOval(centerX, centerY, 20, 20);
		
		myTurtle = new TurtleMascot();
		myTurtleImage = myTurtle.getImage();
		
		fittedX = centerX-myTurtle.getWidth()/2;
		fittedY = centerY-myTurtle.getHeight()/2;
		myTurtle.setX(fittedX);
		myTurtle.setY(fittedY);
		
		myPane.getChildren().add(myBackground);
		//myPane.getChildren().add(myCanvas);
		myPane.getChildren().add(myTurtleImage);
	}

	public Pane getLand() {
		return myPane;
	}
	
	public TurtleMascot getTurtle() {
		return myTurtle;
	}

	public void changeBackground(Color c) {
		gcb.setFill(c);
		gcb.fillRect(0, 0, myWidth, myHeight);
	}
	
	public void changeTurtle(String value) {
		Image newImage = myTurtle.getAnimalMap().get(value);
		myTurtle.setImage(newImage);
		myTurtle.setX(fittedX);
		myTurtle.setY(fittedY);
	}
	
	public void changeTurtle(String name, File file) {
		myTurtle.convertToImage(name, file);
		myTurtle.setX(fittedX);
		myTurtle.setY(fittedY);
	}
	
}
