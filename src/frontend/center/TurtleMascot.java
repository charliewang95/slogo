package frontend.center;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleMascot {
	private final int WIDTH = 25;
	private final int HEIGHT = 25;

	private ImageView myImage;
	private double myX, myY;
	private HashMap<String, Image> myAnimalMap;
	private boolean isDown;

	public TurtleMascot() {
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
	 * @param x
	 */
	public void setX(int x) {
		myX = x;
		myImage.setLayoutX(x);
	}

	/**
	 * @return
	 */
	public double getX() {
		// return myX;
		return myImage.getLayoutX();
	}

	/**
	 * @param y
	 */
	public void setY(int y) {
		myY = y;
		myImage.setLayoutY(y);
	}

	/**
	 * @return
	 */
	public double getY() {
		// return myY;
		return myImage.getLayoutY();
	}

	public double getDirection() {
		return myImage.getRotate();
	}

	/**
	 * @param direction
	 *            - an angle, in degrees, from 0 (inclusive) to 360 (exclusive)
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
