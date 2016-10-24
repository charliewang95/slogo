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
            if (penDown != myPen) {
                myPen = penDown;
                System.out.println(myPen);
                setChanged();
                notifyObservers();
            }
    }

    public boolean getPenStatus() {
            return myPen;
    }
}
