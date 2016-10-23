package frontend.coordinates;

public class LayoutToTurtleLand extends CoordinateConverter {

    public LayoutToTurtleLand (int environmentWidth, int environmentHeight) {
        super(environmentWidth, environmentHeight);
    }

    @Override
    public double convertX (double x) {
        return x - getCenterX();
    }

    @Override
    public double convertY (double y) {
        return -(y - getCenterY());
    }

}
