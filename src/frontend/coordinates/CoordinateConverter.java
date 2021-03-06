package frontend.coordinates;

import javafx.geometry.Point2D;

/**
 * Converts coordinates to another coordinate frame.
 * 
 * @author Niklas Sjoquist
 *
 */
public abstract class CoordinateConverter {
    private int myWidth, myHeight;
    private double centerX, centerY;
    
    /**
     * @param environmentWidth - width of TurtleLand
     * @param environmentHeight - height of TurtleLand
     * @param turtleWidth - width of TurtleMascot
     * @param turtleHeight - height of TurtleMascot
     */
    public CoordinateConverter(int environmentWidth, int environmentHeight) {
        myWidth = environmentWidth;
        myHeight = environmentHeight;
        centerX = environmentWidth/2.0;
        centerY = environmentHeight/2.0;
    }
    
    public int getWidth() {
        return myWidth;
    }
    
    public int getHeight() {
        return myHeight;
    }
    
    /**
     * Converts a point to a different coordinate system.
     * @param x - old coordinate frame
     * @param y - old coordinate frame
     * @return a length-2 array representing the new point relative to the top left corner of TurtleLand:
     *         array[0] - x,
     *         array[1] - y
     */
    public double[] convertPt(int x, int y) {
        double transformedX = convertX(x);
        double transformedY = convertY(y);
        return makePoint(transformedX, transformedY);
    }
    
    public Point2D converPt(int x, int y) {
        double transformedX = convertX(x);
        double transformedY = convertY(y);
        return new Point2D(transformedX,transformedY);
    }
    
    public Point2D convertPt(Point2D point) {
        Point2D transformedPt = new Point2D(convertX(point.getX()),convertY(point.getY()));
        return transformedPt;
    }
    
    public abstract double convertX(double x);
    public abstract double convertY(double y);
    
    /**
     * @return X-coordinate of center point
     */
    protected double getCenterX() {
        return centerX;
    }
    
    /**
     * @return Y-coordinate of center point
     */
    protected double getCenterY() {
        return centerY;
    }
    
    private double[] makePoint(double x, double y) {
        double[] point = {x, y};
        return point;
    }
}
