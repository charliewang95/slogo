package frontend.observers;

import java.util.Observable;
import backend.observables.ObservableDirection;
import frontend.center.TurtleMascot;

/**
 * @author Niklas Sjoquist
 *
 */
public abstract class DirectionObserver extends TurtleObserver {
    private ObservableDirection turtleDirection = null;

    public DirectionObserver(TurtleMascot turtle, ObservableDirection direction) {
            super(turtle);
            turtleDirection = direction;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtleDirection) {
                    // TODO: Rotate Turtle
                    ObservableDirection dir = (ObservableDirection) obs;
                    setDirection(dir.getDirection());
            }
    }
}
