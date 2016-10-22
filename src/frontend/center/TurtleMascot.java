package frontend.center;

import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Niklas Sjoquist
 *
 */
public class TurtleMascot {
    public static final int WIDTH = 39;
    public static final int HEIGHT = 50;
    
    private ImageView myImage;
    private double myX, myY;
    private boolean isDown;
    
    private static CoordinateConverter converter;
    
    public TurtleMascot(CoordinateConverter coordinateConverter) {
        myX = 0;
        myY = 0;
        setImage("turtlemascotcropped.png");
        isDown = true;
        converter = coordinateConverter;
    }
    
    /**
     * @param x - TurtleLand X-coordinate
     */
    public void setX(double x) {
        // TODO: implement toroidal TurtleLand
        myX = x;
        double layoutX = converter.xFromTurtleLandToLayout(x);
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
        double layoutY = converter.yFromTurtleLandToLayout(y);
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
     * Sets the ImageView of the Sprite
     * @param imageFileName
     */
    public void setImage (String imageFileName) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageFileName));
        myImage = new ImageView(image);
        myImage.setPreserveRatio(true);
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
