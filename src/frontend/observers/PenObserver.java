package frontend.observers;

import java.util.Observable;
import backend.observables.ObservablePen;
import frontend.center.TurtleMascot;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Niklas Sjoquist
 *
 */
public class PenObserver extends TurtleObserver {
    private ObservablePen turtlePen = null;

    public PenObserver(TurtleMascot turtle, GraphicsContext gcc, ObservablePen pen) {
            super(turtle,gcc);
            turtlePen = pen;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtlePen) {
                    // Change pen status
                    ObservablePen pen = (ObservablePen) obs;
                    setPenDown(pen.getPenStatus());
            }
    }
}
