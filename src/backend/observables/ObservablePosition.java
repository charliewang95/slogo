package backend.observables;

import java.util.Observable;

/**
 * 
 * @author Niklas Sjoquist
 *
 */
public class ObservablePosition extends Observable {
    private double myX = 0;
    private double myY = 0;

    public ObservablePosition(double x, double y)  {
            myX = x;
            myY = y;
    }

    public void setPosition(double x, double y) {
            setX(x);
            setY(y);
    }

    public void setX(double x) {
            if (x != myX) {
                myX = x;
                
                setChanged();
                notifyObservers();
            }
    }

    public void setY(double y) {
            if (y != myY) {
                myY = y;

                setChanged();
                notifyObservers();
            }
    }

    public double[] getPosition() {
            double[] pos = {getX(), getY()};
            return pos;
    }

    public double getX() {
            return myX;
    }

    public double getY() {
            return myY;
    }
}
