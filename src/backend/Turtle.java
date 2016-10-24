package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observer;
import backend.observables.*;
import frontend.observers.DirectionObserver;
import frontend.observers.PenObserver;
import frontend.observers.PositionObserver;
import frontend.observers.VisibilityObserver;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import main.Playground;

public class Turtle {
	private Playground playGround;
	
	// Observable States
	private ObservableDirection myDirection = new ObservableDirection(0);
	private ObservablePen myPenDown = new ObservablePen(true);
	private ObservablePosition myPosition = new ObservablePosition(0,0);
	private ObservableVisibility myVisibility = new ObservableVisibility(true);
	
	public Turtle(double x, double y, Playground playground) {
	        myPosition.setX(x);
	        myPosition.setY(y);
	        playGround = playground;
	}
	
	public void observeDirection(DirectionObserver o) {
	    myDirection.addObserver(o);
	}
	
	public void observePen(PenObserver o) {
	    myPenDown.addObserver(o);
	}
	
	public void observePosition(PositionObserver o) {
	    myPosition.addObserver(o);
	}
	
	public void observeVisibility(VisibilityObserver o) {
	    myVisibility.addObserver(o);
	}
	
	public void eraseLines() {
		playGround.clearScreen();
	}
	
	public double getMyX() {
		return myPosition.getX();
	}

	public void setMyX(double myX) {
		myPosition.setX(myX);
	}

	public double getMyY() {
	        return myPosition.getY();
	}

	public void setMyY(double myY) {
		myPosition.setY(myY);
	}

	public boolean isPenDown() {
		return myPenDown.getPenStatus();
	}

	public void setPenDown(boolean penDown) {
		myPenDown.setPen(penDown);
	}

	public double getDirection() {
		return myDirection.getDirection();
	}

	public void setDirection(double direction) {
		myDirection.setDirection(direction);
	}

	public boolean isVisible() {
		return myVisibility.getVisibility();
	}

	public void setVisible(boolean visible) {
		myVisibility.setVisibility(visible);
	}
	
	public ObservableDirection getDirObs() {
	        return myDirection;
	}
	
	public ObservablePen getPenObs() {
	        return myPenDown;
	}
	
	public ObservablePosition getPosObs() {
	        return myPosition;
	}
	
	public ObservableVisibility getVisObs() {
	        return myVisibility;
	}
	
}
