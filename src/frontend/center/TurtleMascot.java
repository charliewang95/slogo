package frontend.center;

import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import frontend.ErrorException;
import frontend.coordinates.CoordinateConverter;
import frontend.coordinates.TurtleLandToLayout;
import frontend.coordinates.TurtleToLayout;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;

/**
 * @author Niklas Sjoquist
 * 
 * @modifier Charlie Wang
 */
public class TurtleMascot {
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;

	private ImageView myImage;
	private ResourceBundle myResources;
	private double myX, myY;
	private HashMap<String, Image> myAnimalMap;
	private String myAnimal;
	private Pen myPen;

	private TurtleToLayout converter;

	public TurtleMascot(int environmentWidth, int environmentHeight, TurtleLandToLayout tlConverter) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");

		converter = new TurtleToLayout(environmentWidth, environmentHeight, WIDTH, HEIGHT);
		myImage = new ImageView();
		myX = 0;
		myY = 0;
		myAnimalMap = new HashMap<String, Image>();
		try {
			myAnimalMap.put("Turtle", new Image(getClass().getClassLoader().getResourceAsStream("turtlemascot.png")));
		} catch (Exception e) {
			ErrorException ee = new ErrorException(myResources.getString("NoDefaultImageError") + " (Turtle)");
		}
		try {
			myAnimalMap.put("Elephant",
					new Image(getClass().getClassLoader().getResourceAsStream("elephantmascot.png")));
		} catch (Exception e) {
			ErrorException ee = new ErrorException(myResources.getString("NoDefaultImageError") + " (Elephant)");
		}
		try {
			myAnimalMap.put("Rocket", new Image(getClass().getClassLoader().getResourceAsStream("rocket.png")));
		} catch (Exception e) {
			ErrorException ee = new ErrorException(myResources.getString("NoDefaultImageError") + " (Rocket)");
		}

		setImage("Turtle", myAnimalMap.get("Turtle"));
		myAnimal = "Turtle";
    }
    
    /**
     * Sets coordinates of the Turtle.
     * @param pos - an array representing the coordinates of the Turtle (x=pos[0], y=pos[1]).
     */
    public void setPosition(double[] pos) {
        double x = pos[0]; double y = pos[1];
        setX(x); setY(y);
    }
    
    /**
     * @return an array representing the coordinates of the Turtle.
     */
    public double[] getPosition() {
        double[] pos = {getX(), getY()};
        return pos;
    }
    
    /**
     * @param x - TurtleLand X-coordinate
     */
    public void setX(double x) {
        myX = x;
        double layoutX = converter.convertX(x);
        myImage.setLayoutX(layoutX);
    }
    
    /**
     * @return TurtleLand X-coordinate
     */
    public double getX() {
        return myX;
    }
    
    /**
     * @param y - TurtleLand Y-coordinate
     */
    public void setY(double y) {
        myY = y;
        double layoutY = converter.convertY(y);
        myImage.setLayoutY(layoutY);
    }
    
    /**
     * @return TurtleLand Y-coordinate
     */
    public double getY() {
        return myY;
    }
    
    public double getDirection() {
        return myImage.getRotate();
    }
    
    /**
     * @param direction - an angle, in degrees, 
     *                    from 0 (inclusive) to 360 (exclusive)
     */
    public void setDirection(double direction) {
        myImage.setRotate(direction);
    }


	/**
	 * Convert a file to a image and save it to the map
	 */
	public void convertToImage(String name, File newImage) {
		try {
			BufferedImage bufferedImage = ImageIO.read(newImage);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			addAnimal(name, image);
			setImage(name, image);
		} catch (IOException ex) {
			ErrorException ee = new ErrorException(myResources.getString("FileNotImageError"));
		}
	}

	public String getAnimal () {
		return myAnimal;
	}
	
	/**
	 * Sets the ImageView of the Sprite
	 * 
	 * @param newImage
	 *            the image to be drawn
	 */
	public void setImage(String animal, Image newImage) {
		myAnimal = animal;
		try {
			myImage.setImage(newImage);
			myImage.setFitWidth(WIDTH);
			myImage.setFitHeight(HEIGHT);
		} catch (Exception e) {
			ErrorException ee = new ErrorException(myResources.getString("FileNotImageError"));
		}
	}

	public void setVisible(boolean value) {
		myImage.setVisible(value);
	}
	
	/**
	 * Returns the ImageView of the Sprite
	 * 
	 * @return
	 */
	public ImageView getImage() {
		return myImage;
	}

	public boolean isDrawing() {
		return myPen.isDrawing();
	}

	/**
	 * @param penStatus
	 *            - true sets pen down (draws) false raises pen (stops drawing)
	 */
	public void setDrawing(boolean penStatus) {
		myPen.setDrawing(penStatus);
	}

	public HashMap<String, Image> getAnimalMap() {
		return myAnimalMap;
	}

	public void addAnimal(String key, Image value) {
		myAnimalMap.put(key, value);
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
	
	public void setPen(Pen pen) {
	    myPen = pen;
	}
	
	public Pen getPen() {
	    return myPen;
	}
	
	public void setPenColor(Color c) {
		myPen.setColor(c);
	}
	
	public Color getPenColor() {
	        return myPen.getColor();
	}
	
	public void setPenThickness(double t) {
	        myPen.setThickness(t);
	}
	
	public double getPenThickness() {
	        return myPen.getThickness();
	}
}
