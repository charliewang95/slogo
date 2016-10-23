package backend;

import java.util.List;
import java.util.Observer;
import backend.observables.*;
import frontend.observers.DirectionObserver;
import frontend.observers.PenObserver;
import frontend.observers.PositionObserver;
import frontend.observers.VisibilityObserver;
import main.Playground;

public class Turtle {

	/*private int myX;
	private int myY;
	private boolean penDown;
	private int direction;
	private boolean visable;
	private int xPixFromCenter;
	private int yPixFromCenter;*/
	private Playground playGround;
	
	// Observables
	private ObservableDirection myDirection = new ObservableDirection(0);
	private ObservablePen myPenDown = new ObservablePen(true);
	private ObservablePosition myPosition = new ObservablePosition(0,0);
	private ObservableVisibility myVisibility = new ObservableVisibility(true);
	
	public Turtle(int x, int y, Playground playground, List<Observer> observers) {
	        myPosition.setX(x);
	        myPosition.setY(y);
	        playGround = playground;
	        
	        // Add Observers
	        for (Observer obs : observers) {
	            if (obs.getClass() == DirectionObserver.class) {
	                myDirection.addObserver(obs);
	            } else if (obs.getClass() == PenObserver.class) {
	                myPenDown.addObserver(obs);
	            } else if (obs.getClass() == PositionObserver.class) {
	                myPosition.addObserver(obs);
	            } else if (obs.getClass() == VisibilityObserver.class) {
	                myVisibility.addObserver(obs);
	            }
	        }
	}
	
	public void eraseLines() {
		playGround.clearScreen();
	}
	
	public double getMyX() {
		//return myX;
		return myPosition.getX();
	}

	public void setMyX(double myX) {
		//needs work with lines
		//this.myX = myX;
		myPosition.setX(myX);
	}

	public double getMyY() {
		//return myY;
	        return myPosition.getY();
	}

	public void setMyY(double myY) {
		//needs work with lines
		//this.myY = myY;
		myPosition.setY(myY);
	}

	public boolean isPenDown() {
		//return penDown;
		return myPenDown.getPenStatus();
	}

	public void setPenDown(boolean penDown) {
		//this.penDown = penDown;
		myPenDown.setPen(penDown);
	}

	public double getDirection() {
		//return direction;
		return myDirection.getDirection();
	}

	public void setDirection(double direction) {
		//this.direction = direction;
		myDirection.setDirection(direction);
	}

	public boolean isVisable() {
		//return visable;
		return myVisibility.getVisibility();
	}

	public void setVisable(boolean visible) {
		//this.visable = visible;
		myVisibility.setVisibility(visible);
	}
	
}
