package frontend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleMascot {
    private ImageView myImage;
    private int myX, myY;
    private double myDir;
    private boolean isDown;
    
    public TurtleMascot() {
        myX = 0;
        myY = 0;
        this.setImage("turtlemascot.png");
        myDir = 0;
        isDown = true;
    }
    
    /**
     * @param x
     */
    public void setX(int x) {
        myX = x;
    }
    
    /**
     * @return
     */
    public int getX() {
        return myX;
    }
    
    /**
     * @param y
     */
    public void setY(int y) {
        myY = y;
    }
    
    /**
     * @return
     */
    public int getY() {
        return myY;
    }
    
    public double getDirection() {
        return myDir;
    }
    
    /**
     * @param direction - an angle from 0 (inclusive) to 360 (exclusive)
     */
    public void setDirection(double direction) {
        myDir = direction;
    }
    
    /**
     * Sets the ImageView of the Sprite
     * @param imageFileName
     */
    public void setImage (String imageFileName) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageFileName));
        myImage = new ImageView(image);
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
}
