package frontend.coordinates;

public class TurtleToLayout extends CoordinateConverter {
    private double turtleXoffset, turtleYoffset;

    public TurtleToLayout (int environmentWidth,
                           int environmentHeight,
                           double turtleWidth,
                           double turtleHeight) {
        super(environmentWidth, environmentHeight);
        turtleXoffset = turtleWidth/2.0;
        turtleYoffset = turtleHeight/2.0;
    }

    @Override
    public double convertX (double x) {
        return x + getCenterX() - turtleXoffset;
    }

    @Override
    public double convertY (double y) {
        return (-1)*y + getCenterY() - turtleYoffset;
    }

}
