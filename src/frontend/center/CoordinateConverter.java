package frontend.center;

/**
 * Converts coordinates between TurteLand (0,0 in the center), to layout coordinates (0,0 in the top left).
 * 
 * @author Niklas Sjoquist
 *
 */
public class CoordinateConverter {
    private static final double IMAGE_X_OFFSET = 10;
    private static final double IMAGE_Y_OFFSET = 10;
    
    private double centerX, centerY;
    private double turtleXoffset, turtleYoffset;
    
    /**
     * @param environmentWidth - width of TurtleLand
     * @param environmentHeight - height of TurtleLand
     * @param turtleWidth - width of TurtleMascot
     * @param turtleHeight - height of TurtleMascot
     */
    public CoordinateConverter(int environmentWidth, int environmentHeight, double turtleWidth, double turtleHeight) {
        centerX = environmentWidth/2.0;
        centerY = environmentHeight/2.0;
        turtleXoffset = turtleWidth/2.0 - IMAGE_X_OFFSET;
        turtleYoffset = turtleHeight/2.0 - IMAGE_Y_OFFSET;
    }

    /**
     * Converts a point from TurtleLand coordinates to layout coordinates.
     * @param x - relative to the center
     * @param y - relative to the center
     * @return a length-2 array representing the new point relative to the top left corner of TurtleLand:
     *         array[0] - x,
     *         array[1] - y
     */
    public double[] fromTurtleLandToLayout(double x, double y) {
        double layoutX = xFromTurtleLandToLayout(x);
        double layoutY = yFromTurtleLandToLayout(y);
        return makePoint(layoutX, layoutY);
    }
    
    /**
     * Converts an X-coordinate from TurtleLand to layout coordinates.
     * @param x
     * @return
     */
    public double xFromTurtleLandToLayout(double x) {
        return x + centerX - turtleXoffset;
    }
    
    /**
     * Converts a Y-coordinate from TurtleLand to layout coordinates.
     * @param y
     * @return
     */
    public double yFromTurtleLandToLayout(double y) {
        return (-1)*y + centerY - turtleYoffset;
    }
    
    /**
     * Converts a point from layout coordinates to TurtleLand coordinates.
     * @param x - relative to the top left corner of TurtleLand
     * @param y - relative to the top left corner of TurtleLand
     * @return a length-2 array representing the new point relative to the center of TurtleLand:
     *         array[0] - x,
     *         array[1] - y
     */
    public double[] fromLayoutToTurtleLand(double x, double y) {
        double turtleLandX = xFromLayoutToTurtleLand(x);
        double turtleLandY = yFromLayoutToTurtleLand(y);
        return makePoint(turtleLandX, turtleLandY);
    }
    
    /**
     * Converts an X-coordinate from layout to TurtleLand coordinates.
     * @param x
     * @return
     */
    public double xFromLayoutToTurtleLand(double x) {
        return x - centerX;
    }
    
    /**
     * Converts a Y-coordinate from layout to TurtleLand coordinates.
     * @param y
     * @return
     */
    public double yFromLayoutToTurtleLand(double y) {
        return -(y - centerY);
    }
    
    private double[] makePoint(double x, double y) {
        double[] point = {x, y};
        return point;
    }
}
