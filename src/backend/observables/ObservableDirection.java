package backend.observables;

import java.util.Observable;

/**
 * @author Niklas Sjoquist
 *
 */
public class ObservableDirection extends Observable {
    private double myDirection = 0;

    public ObservableDirection(double direction) {
            myDirection = direction;
    }

    public void setDirection(double degrees) {
            // TODO: make sure value changed
            
            myDirection = degrees;

            setChanged();
            notifyObservers();
    }

    public double getDirection() {
            return myDirection;
    }
}
