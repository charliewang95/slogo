package frontend.coordinates;

/**
 * Converts TurtleLand coordinates to layout coordinates.
 * @author Niklas Sjoquist
 *
 */
public class TurtleLandToLayout extends CoordinateConverter {

    public TurtleLandToLayout (int environmentWidth,
                               int environmentHeight) {
        super(environmentWidth, environmentHeight);
    }

    @Override
    public double convertX (double x) {
        return x + getCenterX();
    }

    @Override
    public double convertY (double y) {
        return (-1)*y + getCenterY();
    }

}
