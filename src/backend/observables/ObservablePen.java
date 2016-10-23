package backend.observables;

import java.util.Observable;

/**
 * @author Niklas Sjoquist
 *
 */
public class ObservablePen extends Observable {
    private boolean myPen = true;

    public ObservablePen(boolean penDown) {
            myPen = penDown;
    }

    public void setPen(boolean penDown) {
            // TODO: make sure value changed

            myPen = penDown;

            setChanged();
            notifyObservers();
    }

    public boolean getPenStatus() {
            return myPen;
    }
}
