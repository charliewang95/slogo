package frontend.center;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import frontend.ErrorException;
import frontend.coordinates.CoordinateConverter;
import frontend.coordinates.TurtleLandToLayout;
import frontend.observers.*;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;

/**
 * @author Charlie Wang
 * 
 * @modifier Niklas Sjoquist
 *
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
	private TurtleLandToLayout converter;

	public TurtleLand() {
		myPane = new Pane();

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myWidth = Integer.parseInt(myResources.getString("CanvasWidth"));
		myHeight = Integer.parseInt(myResources.getString("CanvasHeight"));
		myPane.setPrefSize(myWidth, myHeight);

		converter = new TurtleLandToLayout(myWidth, myHeight);

		centerX = myWidth / 2.0;
		centerY = myHeight / 2.0;

		myCanvas = new Canvas(myWidth, myHeight);
		myBackground = new Canvas(myWidth, myHeight);

		gcb = myBackground.getGraphicsContext2D();
		gcc = myCanvas.getGraphicsContext2D();

		gcb.setFill(defaultGround);
		gcb.fillRect(0, 0, myWidth, myHeight);

		myTurtle = new TurtleMascot(myWidth, myHeight, converter);
		myTurtleImage = myTurtle.getImage();

		// put turtle in center
		myTurtle.setX(0);
		myTurtle.setY(0);

		// Execute a test path
		/*
		 * List<PathElement> testpath = setTestPath(); drawPath(testpath);
		 * testMoveTurtle();
		 */

		myPane.getChildren().add(myBackground);
		myPane.getChildren().add(myCanvas);
		myPane.getChildren().add(myTurtleImage);
	}

	public Pane getLand() {
		return myPane;
	}

	public GraphicsContext getGraphicsContext() {
		return gcc;
	}

	public void reset() {
		gcc.clearRect(0, 0, myWidth, myHeight);
		myTurtle.setX(0);
		myTurtle.setY(0);
		myTurtle.setDirection(0);
		myTurtle.setDrawing(true);
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
	}

	public void changeTurtle(String name, File file) {
		myTurtle.convertToImage(name, file);
	}

	public void setPenColor(Color color) {
		myTurtle.setPenColor(color);
	}

	public void printGround() {
		WritableImage wi = new WritableImage(myWidth, myHeight);
		myBackground.snapshot(null, wi);
		myCanvas.snapshot(null, wi);
		File file = new File("output.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int getWidth() {
	    return myWidth;
	}
	
	public int getHeight() {
	    return myHeight;
	}
}
