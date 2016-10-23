package frontend.observers;

import java.util.Observable;
import backend.observables.ObservablePosition;
import frontend.center.TurtleMascot;

public class PositionObserver extends TurtleObserver {
    private ObservablePosition turtlePosition = null;

    public PositionObserver(TurtleMascot turtle, ObservablePosition position) {
            super(turtle);
            turtlePosition = position;
    }

    public void update(Observable obs, Object obj) {
            if (obs == turtlePosition) {
                    // TODO: Move Turtle to updated position
                    //               Check if pen is down, maybe draw line
                    ObservablePosition pos = (ObservablePosition) obs;
                    setPosition(pos.getPosition());
                    
                    if (/*pen is down*/) {
                            // TODO: draw line to new position
                            
                    }
                    else {
                            // TODO: move path to new position (moveTo) or store a previous position
                    }
            }
    }
}
