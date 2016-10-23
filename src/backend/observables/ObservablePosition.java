package backend.observables;

import java.util.Observable;

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
            // TODO: make sure value changed
            // TODO: use helper method
            
            myX = x;

            setChanged();
            notifyObservers();
    }

    private void setX(double x, boolean notifyFlag) {
            myX = x;

            setChanged();
            if (notifyFlag) {
                    notifyObservers();
            }
    }

    public void setY(double y) {
            // TODO: make sure value changed
            // TODO: use helper method
            
            myY = y;

            setChanged();
            notifyObservers();
    }

    private void setY(double y, boolean notifyFlag) {
            myY = y;

            setChanged();
            if (notifyFlag) {
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
