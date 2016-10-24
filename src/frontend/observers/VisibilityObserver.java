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

    public VisibilityObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height, ObservableVisibility visible) {
            super(turtle, gcc,width,height);
            turtleVisibility = visible;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtleVisibility) {
                ObservableVisibility vis = (ObservableVisibility) obs;
                setVisibility(vis.getVisibility());
            }
    }
}
