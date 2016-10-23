package backend.observables;

import java.util.Observable;

/**
 * Visibility of the Turtle.
 * @author Niklas Sjoquist
 *
 */
public class ObservableVisibility extends Observable {
    private boolean myVisibility = true;

    public ObservableVisibility(boolean visible) {
            myVisibility = visible;
    }

    public void setVisibility(boolean value) {
            if (value != myVisibility) {
                myVisibility = value;

                setChanged();
                notifyObservers();
            }
    }

    public boolean getVisibility() {
            return myVisibility;
    }
}
