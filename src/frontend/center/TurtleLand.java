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

/**
 * @author Charlie Wang
 * 
 * @modifier Niklas Sjoquist
 */
public class TurtleLand {
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Color defaultGround = Color.LIGHTGREEN;
	private Pane myPane;
	private Canvas myCanvas, myBackground;
	private TurtleMascot myTurtle;
	private ImageView myTurtleImage;
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

		converter = new CoordinateConverter(myWidth, myHeight, TurtleMascot.WIDTH, TurtleMascot.HEIGHT);

		centerX = myWidth / 2.0;
		centerY = myHeight / 2.0;

		myCanvas = new Canvas(myWidth, myHeight);
		myBackground = new Canvas(myWidth, myHeight);

		gcb = myBackground.getGraphicsContext2D();
		gcc = myCanvas.getGraphicsContext2D();

		gcb.setFill(defaultGround);
		gcb.fillRect(0, 0, myWidth, myHeight);

		gcc.setFill(Color.RED);
		/*
		 * gcc.fillOval(centerX-10, centerY-10, 20, 20); gcc.setLineWidth(2);
		 * gcc.setFill(Color.BLUE); gcc.strokeRect(centerX-20, centerY-25, 40,
		 * 50);
		 */ // encloses the turtle in the center

		myTurtle = new TurtleMascot(converter);
		myTurtleImage = myTurtle.getImage();

		myTurtle.setX(0);
		myTurtle.setY(0);
		
		right45fd100();
		
		myPane.getChildren().add(myBackground);
		myPane.getChildren().add(myCanvas);
		myPane.getChildren().add(myTurtleImage);
	}
	
	private void right45fd100() {
	    myTurtle.setDirection(45);
	    myTurtle.setX(100);
	    myTurtle.setY(100);
	    gcc.setLineWidth(2);
	    gcc.setFill(Color.BLUE);
	    gcc.strokeLine(centerX, centerY, converter.xFromTurtleLandToLayout(100), converter.yFromTurtleLandToLayout(100));
	    
	    /*gcc.beginPath();
	    gcc.moveTo(centerX, centerY);
	    gcc.lineTo(converter.xFromTurtleLandToLayout(100), converter.yFromTurtleLandToLayout(100));
	    gcc.closePath();
	    gcc.stroke();*/
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
		myTurtle.setX(0);
		myTurtle.setY(0);
	}

	public void changeTurtle(String name, File file) {
		myTurtle.convertToImage(name, file);
		myTurtle.setX(0);
		myTurtle.setY(0);
	}
}
