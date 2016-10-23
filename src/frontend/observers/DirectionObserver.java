package frontend.observers;

import java.util.Observable;
import backend.observables.ObservableDirection;
import frontend.center.TurtleMascot;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Niklas Sjoquist
 *
 */
public class DirectionObserver extends TurtleObserver {
    private ObservableDirection turtleDirection = null;

    public DirectionObserver(TurtleMascot turtle, GraphicsContext gcc, ObservableDirection direction) {
            super(turtle,gcc);
            turtleDirection = direction;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtleDirection) {
                    // Rotate Turtle
                    ObservableDirection dir = (ObservableDirection) obs;
                    setDirection(dir.getDirection());
            }
    }
}
