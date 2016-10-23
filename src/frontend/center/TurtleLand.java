package frontend.center;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import frontend.ErrorException;
import frontend.coordinates.CoordinateConverter;
import frontend.coordinates.TurtleLandToLayout;
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
	private Color myPenColor = Color.BLACK;
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

		myTurtle = new TurtleMascot(myWidth, myHeight);
		myTurtleImage = myTurtle.getImage();

		// put turtle in center
		myTurtle.setX(0);
		myTurtle.setY(0);

		// Execute a test path
		List<PathElement> testpath = setTestPath();
		drawPath(testpath);
		testMoveTurtle();

		myPane.getChildren().add(myBackground);
		myPane.getChildren().add(myCanvas);
		myPane.getChildren().add(myTurtleImage);
	}

	private List<PathElement> setTestPath() {
		List<PathElement> path = myTurtle.getPenPath();
		path.add(new MoveTo(converter.convertX(0), converter.convertY(0)));
		path.add(new LineTo(converter.convertX(50), converter.convertY(100)));
		path.add(new LineTo(converter.convertX(-100), converter.convertY(-40)));
		return path;
	}

	private void testMoveTurtle() {
		// set position
		myTurtle.setX(-100);
		myTurtle.setY(-40);

		// set bearing
		Point2D startPt = new Point2D(50, 100);
		Point2D endPt = new Point2D(-100, -40);
		myTurtle.setDirection(getSegmentBearing(startPt, endPt));
	}

	private void drawPath(List<PathElement> path) {
		gcc.setStroke(myPenColor);
		gcc.setLineWidth(1);

		// List<PathElement> pathList = myTurtle.getPenPath();

		gcc.beginPath();
		path.stream().forEach((pe) -> {
			if (pe.getClass() == MoveTo.class) {
				gcc.moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
			} else if (pe.getClass() == LineTo.class) {
				gcc.lineTo(((LineTo) pe).getX(), ((LineTo) pe).getY());
			}
		});
		gcc.stroke();
		gcc.closePath();
	}

	/**
	 * Returns the bearing direction (in degrees) from a starting point to and
	 * ending point.
	 * 
	 * @param startingPoint
	 * @param endingPoint
	 * @return bearing direction, in degrees, from 0 to 360
	 */
	private double getSegmentBearing(Point2D startingPoint, Point2D endingPoint) {
		Point2D originPoint = new Point2D(endingPoint.getX() - startingPoint.getX(),
				endingPoint.getY() - startingPoint.getY()); // get origin point
															// to origin by
															// subtracting end
															// from start
		double bearingRadians = Math.atan2(originPoint.getY(), originPoint.getX()); // get
																					// bearing
																					// in
																					// radians
		double bearingDegrees = bearingRadians * (180.0 / Math.PI); // convert
																	// to
																	// degrees
		bearingDegrees = (bearingDegrees > 0.0 ? bearingDegrees : (360.0 + bearingDegrees)); // correct
																								// discontinuity
		return bearingDegrees;
	}

	public Pane getLand() {
		return myPane;
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
		myPenColor = color;
	}

	public void printGround() {
		WritableImage wi = new WritableImage(myWidth, myHeight);
		myBackground.snapshot(null, wi);
		myCanvas.snapshot(null, wi);
		File file = new File("output.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
		} catch (Exception e) {
			// TODO:
		}
	}
}
