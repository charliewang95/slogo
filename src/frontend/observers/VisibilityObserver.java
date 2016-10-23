package frontend.observers;

import java.util.Observable;
import backend.observables.ObservableVisibility;
import frontend.center.TurtleMascot;

/**
 * @author Niklas Sjoquist
 *
 */
public class VisibilityObserver extends TurtleObserver {
    private ObservableVisibility turtleVisibility = null;

    public VisibilityObserver(TurtleMascot turtle, ObservableVisibility visible) {
            super(turtle);
            turtleVisibility = visible;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtleVisibility) {
                    // TODO: Show/Hide Turtle
                    //      may need to track oldX,oldY when we show Turtle after hiding??
            }
    }
}
