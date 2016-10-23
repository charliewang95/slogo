package frontend.center;

import java.util.ResourceBundle;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Niklas Sjoquist
 * 
 * @modifier Charlie Wang
 */
public class TurtleMascot {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    
    private ImageView myImage;
    private double myX, myY;
    private HashMap<String, Image> myAnimalMap;
    private boolean isDown;
    
    private static CoordinateConverter converter;
    
    public TurtleMascot(CoordinateConverter coordinateConverter) {
        converter = coordinateConverter;
        
        myImage = new ImageView();
		myX = 0;
		myY = 0;
		myAnimalMap = new HashMap<String, Image>();
		myAnimalMap.put("Turtle", new Image(getClass().getClassLoader().getResourceAsStream("turtlemascot.png")));
		myAnimalMap.put("Elephant", new Image(getClass().getClassLoader().getResourceAsStream("elephantmascot.png")));
		myAnimalMap.put("Rocket", new Image(getClass().getClassLoader().getResourceAsStream("rocket.png")));
		setImage(myAnimalMap.get("Turtle"));
		isDown = true;
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
        // TODO: implement toroidal TurtleLand
        myX = x;
        double layoutX = converter.xFromTurtleToLayout(x);
        myImage.setLayoutX(layoutX);
    }
    
    /**
     * @return TurtleLand X-coordinate
     */
    public double getX() {
        return myX;
        //return myImage.getLayoutX(); //returns the layout X-coord
    }
    
    /**
     * @param y - TurtleLand Y-coordinate
     */
    public void setY(double y) {
        // TODO: implement toroidal TurtleLand
        myY = y;
        double layoutY = converter.yFromTurtleToLayout(y);
        myImage.setLayoutY(layoutY);
    }
    
    /**
     * @return TurtleLand Y-coordinate
     */
    public double getY() {
        return myY;
        //return myImage.getLayoutY(); //returns the layout Y-coord
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
			setImage(image);
		} catch (IOException ex) {
			// TODO
			// Logger.getLogger(JavaFXPixel.class.getName()).log(Level.SEVERE,
			// null, ex);
		}
	}

	/**
	 * Sets the ImageView of the Sprite
	 * 
	 * @param newImage
	 *            the image to be drawn
	 */
	public void setImage(Image newImage) {
		try {
			myImage.setImage(newImage);
			myImage.setFitWidth(WIDTH);
			myImage.setFitHeight(HEIGHT);
		} catch (Exception e) {
			//TODO
		}
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
		return isDown;
	}

	/**
	 * @param penStatus
	 *            - true sets pen down (draws) false raises pen (stops drawing)
	 */
	public void setDrawing(boolean penStatus) {
		isDown = penStatus;
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
}
