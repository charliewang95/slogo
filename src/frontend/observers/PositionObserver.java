package frontend.observers;

import java.util.Observable;
import backend.Turtle;
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

    public PositionObserver(TurtleMascot turtleView, Turtle turtleModel, GraphicsContext gcc, int width, int height) {
            super(turtleView,turtleModel,gcc,width,height);
            turtlePosition = turtleModel.getPosObs();
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtlePosition) {
                    // Move Turtle to updated position;
                    // Draw line if pen is down
                
                    ObservablePosition pos = (ObservablePosition) obs;
                    Point2D start = new Point2D(getMascotX(),getMascotY());
                    Point2D end = new Point2D(pos.getX(),pos.getY());
                    
                    System.out.println("Start Pt: "+start.toString()+";\tEnd Pt: "+end.toString());
                    
                    moveTurtle(start,end);
            }
    }
}
