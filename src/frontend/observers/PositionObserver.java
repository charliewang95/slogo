package frontend.observers;

import java.util.Observable;
import backend.observables.ObservablePosition;
import frontend.center.TurtleMascot;
import frontend.coordinates.CoordinateConverter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 * @author Niklas Sjoquist
 *
 */
public class PositionObserver extends TurtleObserver {
    private ObservablePosition turtlePosition = null;

    public PositionObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height, ObservablePosition position) {
            super(turtle,gcc,width,height);
            turtlePosition = position;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtlePosition) {
                    // Move Turtle to updated position;
                    // Check if pen is down, maybe draw line
                    ObservablePosition pos = (ObservablePosition) obs;
                    Point2D start = new Point2D(getMascotX(),getMascotY());
                    Point2D end = new Point2D(pos.getX(),pos.getY());
                    
                    moveTurtle(start,end);
            }
    }
}
