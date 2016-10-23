package frontend.observers;

import java.util.Observable;
import backend.observables.ObservablePen;
import frontend.center.TurtleMascot;

public class PenObserver extends TurtleObserver {
    private ObservablePen turtlePen = null;

    public PenObserver(TurtleMascot turtle, ObservablePen pen) {
            super(turtle);
            turtlePen = pen;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtlePen) {
                    // TODO: change pen
                    ObservablePen pen = (ObservablePen) obs;
                    setPenDown(pen.getPenStatus());
            }
    }
}
