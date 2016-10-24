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
            setX(x,false);
            setY(y,false);
            // wait until both x,y have changed to notify
            setChanged();
            notifyObservers();
    }

    public void setX(double x) {
            setX(x,true);
    }
    
    public void setX(double x, boolean notify) {
            if (x != myX) {
                myX = x;
                if (notify) {
                    setChanged();
                    notifyObservers();
                }
            }
    }
    
    public void setY(double y) {
            setY(y,true);
    }

    public void setY(double y, boolean notify) {
            if (y != myY) {
                myY = y;
                if (notify) {
                    setChanged();
                    notifyObservers();
                }
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
