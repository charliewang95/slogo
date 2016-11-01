package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observer;
import backend.observables.*;
import frontend.observers.DirectionObserver;
import frontend.observers.OutputObserver;
import frontend.observers.PenObserver;
import frontend.observers.PositionObserver;
import frontend.observers.VisibilityObserver;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import main.Playground;

public class Turtle {
	private Playground playGround;
	
	// Observable States
	private ObservableDirection myDirection = new ObservableDirection(0);
	private ObservablePen myPenDown = new ObservablePen(true);
	private ObservablePosition myPosition = new ObservablePosition(0,0);
	private ObservableVisibility myVisibility = new ObservableVisibility(true);
	private Communication myOutput = new Communication();
	
	/*
	private ObservableShape myShape = new ObservableShape(0);
	private ObservablePenColor myPenColor = new ObservablePenColor(0);
	private ObservableBackgroundColor myBackgroundColor = new ObservableBackgroundColor(0);
	private ObservablePenSize myPenSize = new ObservablePenSize(5);
	*/
	
	public Turtle(double x, double y, Playground playground) {
	        myPosition.setX(x);
	        myPosition.setY(y);
	        playGround = playground;
	}
	
	public String getShape() {
		//return myShape.getShape();
		return null;
	}
	
	public String getPenColor() {
		//return myPenColor.getColor();
		return null;
	}
	
	public void setBackground(int index) {
		//myBackgroundColor.getColor(index);
	}
	
	public void setPalette(int index, int r, int g, int b) {
		//myBackgroundColor.changeIndex(index,r,g,b);
	}
	
	public void setPenColor(int index) {
		//myPenColor.setColor(index);
	}
	
	public void setPenSize(int pix) {
		//myPenSize.setSize(pix);
	}
	
	public void setShape(int index) {
		//myShape.setShape(index);
	}
	
	public void observeDirection(DirectionObserver o) {
	    myDirection.addObserver(o);
	}
	
	public void observeOutput(OutputObserver o) {
	    myOutput.addObserver(o);
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
		myDirection.setDirection(0);
	}
	
	public Point2D getMyPos() {
		return new Point2D(getMyX(),getMyY());
	}
	
	public void setMyPos(double x, double y) {
		myPosition.setPosition(x, y);
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
	
	public Communication getOutObs() {
	        return myOutput;
	}
	
}
