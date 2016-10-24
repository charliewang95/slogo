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

    public DirectionObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height, ObservableDirection direction) {
            super(turtle,gcc,width,height);
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
