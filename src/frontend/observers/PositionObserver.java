package frontend.observers;

import java.util.Observable;
import backend.observables.ObservablePosition;
import frontend.center.TurtleMascot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 * @author Niklas Sjoquist
 *
 */
public class PositionObserver extends TurtleObserver {
    private ObservablePosition turtlePosition = null;

    public PositionObserver(TurtleMascot turtle, GraphicsContext gcc, ObservablePosition position) {
            super(turtle,gcc);
            turtlePosition = position;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtlePosition) {
                    // Move Turtle to updated position;
                    // Check if pen is down, maybe draw line
                    ObservablePosition pos = (ObservablePosition) obs;
                    setPosition(pos.getPosition());
                    
                    if (getPenStatus()) {
                            // TODO: draw line to new position
                            addPathElement(new LineTo(pos.getX(),pos.getY()));
                    }
                    else {
                            // TODO: move path to new position (moveTo) or store a previous position
                            addPathElement(new MoveTo(pos.getX(),pos.getY()));
                    }
            }
    }
}
