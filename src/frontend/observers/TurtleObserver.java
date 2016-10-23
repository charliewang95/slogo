package frontend.observers;

import java.util.Observer;
import frontend.center.TurtleMascot;

public abstract class TurtleObserver implements Observer {
    private TurtleMascot myTurtle;

    public TurtleObserver(TurtleMascot turtle) {
            myTurtle = turtle;
    }

    ///////////////////////////////////////////////////////////////
    // Only subclasses should be able to access the TurtleMascot //
    ///////////////////////////////////////////////////////////////

    protected void setPosition(double[] pos) {
            myTurtle.setPosition(pos);
    }

    protected void setX(double x) {
            myTurtle.setX(x);
    }

    protected void setY(double y) {
            myTurtle.setY(y);
    }

    protected void setDirection(double degrees) {
            myTurtle.setDirection(degrees);
    }

    protected void setVisibility(boolean visible) {
            // TODO: access visibility
    }

    protected void setPenDown(boolean penDown) {
            myTurtle.setDrawing(penDown);
    }
}
