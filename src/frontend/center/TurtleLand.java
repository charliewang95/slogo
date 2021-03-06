package frontend.center;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import frontend.Display;
import frontend.ErrorException;
import frontend.coordinates.TurtleLandToLayout;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

	private Color myBackgroundColor;
	
	private Pane myPane;
	private Display myDisplay;
	private Pen myPen;
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
		myBackgroundColor = defaultGround;
		gcb.fillRect(0, 0, myWidth, myHeight);

		myTurtle = new TurtleMascot(myWidth, myHeight, converter);
		myTurtleImage = myTurtle.getImage();
		myPen = new Pen(gcc, converter, myTurtle);
		myTurtle.setPen(myPen);
		// put turtle in center
		myTurtle.setX(0);
		myTurtle.setY(0);
		updateText();

		myPane.getChildren().add(myBackground);
		myPane.getChildren().add(myCanvas);
		myPane.getChildren().add(myText);
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
		myBackgroundColor = c;
		gcb.fillRect(0, 0, myWidth, myHeight);
	}
	
	public void changeBackground(String stringc) {
		Color c = parseColor(stringc);
		changeBackground(c);
	}
	
	public Color parseColor(String stringc) {
		int r = Integer.parseInt(stringc.substring(2,4), 16);
		int g = Integer.parseInt(stringc.substring(4,6), 16);
		int b = Integer.parseInt(stringc.substring(6,8), 16);
		Color c = Color.rgb(r,g,b);
		return c;
	}
	
	public Color getBackgroundColor() {
		return myBackgroundColor;
	}

	public void changeTurtle(String value) {
		Image newImage = myTurtle.getAnimalMap().get(value);
		myTurtle.setImage(value, newImage);
	}

	public void changeTurtle(String name, File file) {
		myTurtle.convertToImage(name, file);
	}

	public void setPenColor(Color color) {
		myTurtle.setPenColor(color);
	}
	
	public void setPenColor(String stringc) {
		Color c = parseColor(stringc);
		setPenColor(c);
	}

	public void printGround() {
		WritableImage wi = new WritableImage(myWidth, myHeight);
		myBackground.snapshot(null, wi);
		myCanvas.snapshot(null, wi);
		File file = new File("images/output.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
		} catch (IOException e) {
			ErrorException ee = new ErrorException(myResources.getString("SaveImageErrors"));
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
		// Round double values
		double roundedX = roundToNearestHundredth(myTurtle.getX());
		double roundedY = roundToNearestHundredth(myTurtle.getY());
		double roundedDir = roundToNearestHundredth(myTurtle.getDirection());
		double penSize =  roundToNearestHundredth(myPen.getThickness());
		// Set text
		String xText = "x: " + roundedX + "\n";
		String yText = "y: " + roundedY + "\n";
		String dirText = "direction: " + roundedDir + "\n";
		String pen = myTurtle.isDrawing()?"down":"up";
		String penText = "pen: " + pen + "\n";
		String penSizeText = "pen size: " + penSize;
		String out = xText + yText + dirText + penText + penSizeText;
		gct.setFont(new Font("Verdana", 9));
		gct.fillText(out, 0, 10);
	}
	
	private double roundToNearestHundredth(double a) {
	    return Math.round(a*100)/100.0;
	}

	public void toggleParameters() {
		myText.setVisible(!myText.isVisible());
	}
	
	public void setPenSize(double size) {
		myPen.setThickness(size);
	}
	
	public void setDash() {
		myPen.setDash();
	}
	
	public void setSolid() {
		myPen.setSolid();
	}
	
	public void setPenDown() {
		myTurtle.setDrawing(true);
	}
	
	public void setPenUp() {
		myTurtle.setDrawing(false);
	}
}
