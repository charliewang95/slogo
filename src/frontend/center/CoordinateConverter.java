package frontend.center;

public class CoordinateConverter {
    private int centerX, centerY;
    
    /**
     * Converts coordinates between TurteLand (0,0 in the center),
     * to layout coordinates (0,0 in the top left).
     * @param width
     * @param height
     */
    public CoordinateConverter(int width, int height) {
        centerX = width/2;
        centerY = height/2;
    }

    /**
     * Converts a point from TurtleLand coordinates to layout coordinates.
     * @param x - relative to the center
     * @param y - relative to the center
     * @return a length-2 array representing the new point relative to the top left corner of TurtleLand:
     *         array[0] - x,
     *         array[1] - y
     */
    public int[] fromTurtleLandToLayout(int x, int y) {
        int layoutX = x + centerX;
        int layoutY = y + centerY;
        return makePoint(layoutX, layoutY);
    }
    
    /**
     * Converts an X-coordinate from TurtleLand to layout coordinates
     * @param x
     * @return
     */
    public int xFromTurtleLandToLayout(int x) {
        return x + centerX;
    }
    
    /**
     * Converts a Y-coordinate from TurtleLand to layout coordinates
     * @param y
     * @return
     */
    public int yFromTurtleLandToLayout(int y) {
        return y + centerY;
    }
    
    /**
     * Converts a point from layout coordinates to TurtleLand coordinates.
     * @param x - relative to the top left corner of TurtleLand
     * @param y - relative to the top left corner of TurtleLand
     * @return a length-2 array representing the new point relative to the center of TurtleLand:
     *         array[0] - x,
     *         array[1] - y
     */
    public int[] fromLayoutToTurtleLand(int x, int y) {
        int turtleLandX = x - centerX;
        int turtleLandY = y - centerY;
        return makePoint(turtleLandX, turtleLandY);
    }
    
    private int[] makePoint(int x, int y) {
        int[] point = {x, y};
        return point;
    }
}
