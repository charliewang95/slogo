package frontend.observers;

import java.util.Observable;
import backend.Turtle;
import backend.observables.ObservableVisibility;
import frontend.center.TurtleMascot;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Niklas Sjoquist
 *
 */
public class VisibilityObserver extends TurtleObserver {
    private ObservableVisibility turtleVisibility = null;

    public VisibilityObserver(TurtleMascot turtleView, Turtle turtleModel, GraphicsContext gcc, int width, int height) {
            super(turtleView,turtleModel,gcc,width,height);
            turtleVisibility = turtleModel.getVisObs();
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtleVisibility) {
                ObservableVisibility vis = (ObservableVisibility) obs;
                setVisibility(vis.getVisibility());
            }
    }
}
