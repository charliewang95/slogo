package frontend.center;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleMascot {
    public static final int WIDTH = 75;
    public static final int HEIGHT = 75;
    
    private ImageView myImage;
    private double myX, myY;
    private boolean isDown;
    
    public TurtleMascot() {
        myX = 0;
        myY = 0;
        setImage("turtlemascot.png");
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
}
