package frontend.observers;

import java.util.Observable;
import backend.observables.ObservableVisibility;
import frontend.center.TurtleMascot;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Niklas Sjoquist
 *
 */
public class VisibilityObserver extends TurtleObserver {
    private ObservableVisibility turtleVisibility = null;

    public VisibilityObserver(TurtleMascot turtle, GraphicsContext gcc, ObservableVisibility visible) {
            super(turtle, gcc);
            turtleVisibility = visible;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtleVisibility) {
                    // TODO: Show/Hide Turtle
                    //      may need to track oldX,oldY when we show Turtle after hiding??
            }
    }
}
