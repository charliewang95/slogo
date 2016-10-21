package frontend.center;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleMascot {
    public final int WIDTH = 50;
    public final int HEIGHT = 50;
    
    private ImageView myImage;
    private String myIcon;
    private double myX, myY;
    private HashMap<String, String> myAnimalMap;
    private boolean isDown;
    
    public TurtleMascot() {
        myX = 0;
        myY = 0;
        myAnimalMap = new HashMap<String, String>();
        myAnimalMap.put("Turtle", "turtlemascot.png");
        myAnimalMap.put("Elephant", "elephantmascot.png");
        setImage(myAnimalMap.get("Turtle"));
        //setImage(myAnimalMap.get("Elephant"));
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
        //return myX;
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
        //return myY;
        return myImage.getLayoutY();
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
     * Sets the ImageView of the Sprite
     * @param imageFileName
     */
    public void setImage (String imageFileName) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageFileName));
        
        myImage = new ImageView(image);
        myImage.setFitWidth(WIDTH);
        myImage.setFitHeight(HEIGHT);
    }
    
    /**
     * Returns the ImageView of the Sprite 
     * @return
     */
    public ImageView getImage () {
        return myImage;
    }
    
    public boolean isDrawing() {
        return isDown;
    }
    
    /**
     * @param penStatus - true sets pen down (draws)
     *                    false raises pen (stops drawing)
     */
    public void setDrawing(boolean penStatus) {
        isDown = penStatus;
    }
    
    public HashMap<String, String> getAnimalMap() {
    	return myAnimalMap;
    }
    
    public void addAnimal(String key, String value) {
    	myAnimalMap.put(key, value);
    }
}
