package frontend.center;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import frontend.Display;
import frontend.ErrorException;
import frontend.coordinates.CoordinateConverter;
import frontend.coordinates.TurtleLandToLayout;
import frontend.observers.*;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.text.Font;

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
	private Display myDisplay;
	private Canvas myCanvas, myBackground, myText;
	private TurtleMascot myTurtle;
	private ImageView myTurtleImage;
	private int myWidth;
	private int myHeight;
	private ResourceBundle myResources;
	private GraphicsContext gcb, gcc;
	private TurtleLandToLayout converter;

	public TurtleLand(Display display) {
		myPane = new Pane();
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myWidth = Integer.parseInt(myResources.getString("CanvasWidth"));
		myHeight = Integer.parseInt(myResources.getString("CanvasHeight"));
		myPane.setPrefSize(myWidth, myHeight);

		converter = new TurtleLandToLayout(myWidth, myHeight);

		myCanvas = new Canvas(myWidth, myHeight);
		myBackground = new Canvas(myWidth, myHeight);
		myText = new Canvas(myWidth, myHeight);

		gcb = myBackground.getGraphicsContext2D();
		gcc = myCanvas.getGraphicsContext2D();

		gcb.setFill(defaultGround);
		gcb.fillRect(0, 0, myWidth, myHeight);

		myTurtle = new TurtleMascot(myWidth, myHeight, converter);
		myTurtleImage = myTurtle.getImage();
		// put turtle in center
		myTurtle.setX(0);
		myTurtle.setY(0);
		updateText();
		// Execute a test path
		/*
		 * List<PathElement> testpath = setTestPath(); drawPath(testpath);
		 * testMoveTurtle();
		 */

		myPane.getChildren().add(myBackground);
		myPane.getChildren().add(myCanvas);
		myPane.getChildren().add(myText);
		myPane.getChildren().add(myTurtleImage);
	}

	// The following commented methods that draw a path
	// have been moved to the TurtleObserver class
	// (left here for testing purposes)

	/*
	 * private List<PathElement> setTestPath() { List<PathElement> path =
	 * myTurtle.getPenPath(); path.add(new
	 * MoveTo(converter.convertX(0),converter.convertY(0))); path.add(new
	 * LineTo(converter.convertX(50),converter.convertY(100))); path.add(new
	 * LineTo(converter.convertX(-100),converter.convertY(-40))); return path; }
	 */

	/*
	 * private void testMoveTurtle() { // set position myTurtle.setX(-100);
	 * myTurtle.setY(-40);
	 * 
	 * // set bearing Point2D startPt = new Point2D(50,100); Point2D endPt = new
	 * Point2D(-100,-40);
	 * myTurtle.setDirection(getSegmentBearing(startPt,endPt)); }
	 */
	/*
	 * These methods moved to TurtleObserver class, left here for testing
	 * private void drawPath(List<PathElement> path) {
	 * //gcc.setStroke(myPenColor); //gcc.setLineWidth(1);
	 * 
	 * //List<PathElement> pathList = myTurtle.getPenPath();
	 * 
	 * gc.beginPath(); path.stream().forEach((pe) -> { if (pe.getClass() ==
	 * MoveTo.class) { gc.moveTo(((MoveTo)pe).getX(), ((MoveTo)pe).getY()); }
	 * else if (pe.getClass() == LineTo.class) { gc.lineTo(((LineTo)pe).getX(),
	 * ((LineTo)pe).getY()); } }); gc.stroke(); gc.closePath(); }
	 */
	/*
	 * /** Returns the bearing direction (in degrees) from a starting point to
	 * and ending point.
	 * 
	 * @param startingPoint
	 * 
	 * @param endingPoint
	 * 
	 * @return bearing direction, in degrees, from 0 to 360
	 *//*
		 * private double getSegmentBearing(Point2D startingPoint, Point2D
		 * endingPoint) { Point2D originPoint = new Point2D(endingPoint.getX() -
		 * startingPoint.getX(), endingPoint.getY() - startingPoint.getY()); //
		 * get origin point to origin by subtracting end from start double
		 * bearingRadians = Math.atan2(originPoint.getY(), originPoint.getX());
		 * // get bearing in radians double bearingDegrees = bearingRadians *
		 * (180.0 / Math.PI); // convert to degrees bearingDegrees =
		 * (bearingDegrees > 0.0 ? bearingDegrees : (360.0 + bearingDegrees));
		 * // correct discontinuity return bearingDegrees; }
		 */

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

	public void updateText() {
		GraphicsContext gct = myText.getGraphicsContext2D();
		gct.clearRect(0, 0, myWidth, myHeight);
		String xText = "x: " + myTurtle.getX() + "\n";
		String yText = "y: " + myTurtle.getY() + "\n";
		String dirText = "direction: " + myTurtle.getDirection() + "\n";
		String pen = myTurtle.isDrawing()?"down":"up";
		String penText = "pen: " + pen;
		String out = xText + yText + dirText + penText;
		gct.setFont(new Font("Verdana", 10));
		gct.fillText(out, 0, 10	);
	}

	public void toggleParameters() {
		myText.setVisible(!myText.isVisible());
	}
}
