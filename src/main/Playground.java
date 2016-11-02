package main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observer;

import backend.Interpreter;
import backend.Turtle;
import backend.observables.ObservableDirection;
import backend.observables.ObservablePen;
import backend.observables.ObservablePosition;
import backend.observables.ObservableVisibility;
import frontend.Display;
import frontend.center.TurtleLand;
import frontend.center.TurtleMascot;
import frontend.observers.*;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Playground {
	private Display myDisplay;
	private Stage myStage;
	private Interpreter myInterpreter;
	private Turtle myTurtle;
	private Collection<Observer> myObservers;

	public Playground(Stage s) {
		myStage = s;
	}

	public void init() {
		// initialize backend
		myTurtle = new Turtle(0, 0, this);
		myInterpreter = new Interpreter(this, myTurtle);

		// initialize frontend
		myDisplay = new Display(myStage);
		myDisplay.init(myInterpreter);

		// link frontend/backend
		myObservers = initializeObservers(myDisplay.getTurtleLand(), myTurtle, myDisplay);
		observeObservables(myObservers);
	}

	private int observeObservables(Iterable<Observer> observers) {
		int count = 0;
		for (Observer obs : observers) {
			if (obs.getClass() == DirectionObserver.class) {
				myTurtle.observeDirection((DirectionObserver) obs);
				count++;
			} else if (obs.getClass() == PenObserver.class) {
				myTurtle.observePen((PenObserver) obs);
				count++;
			} else if (obs.getClass() == PositionObserver.class) {
				myTurtle.observePosition((PositionObserver) obs);
				count++;
			} else if (obs.getClass() == VisibilityObserver.class) {
				myTurtle.observeVisibility((VisibilityObserver) obs);
				count++;
			} else if (obs.getClass() == OutputObserver.class) {
				myTurtle.observeOutput((OutputObserver) obs);
				count++;
			}
		}
		return count;
	}

	/**
	 * Creates an observer for each observable.
	 * 
	 * @param turtle
	 *            - view
	 * @param gc
	 *            - graphics context of the canvas of turtleland
	 * @param observables
	 *            - turtle states
	 * @return
	 */
	private Collection<Observer> initializeObservers(TurtleLand turtleLand, Turtle turtleModel, Display display) {
		TurtleMascot turtle = turtleLand.getTurtle();
		GraphicsContext gc = turtleLand.getGraphicsContext();

		int width = turtleLand.getWidth();
		int height = turtleLand.getHeight();

		// TODO: change Observers to take in Display instead of TurtleLand

		Collection<Observer> observers = new ArrayList<>();
		observers.add(new DirectionObserver(turtle, gc, width, height, turtleModel.getDirObs()));
		observers.add(new PenObserver(turtle, gc, width, height, turtleModel.getPenObs()));
		observers.add(new PositionObserver(turtle, gc, width, height, turtleModel.getPosObs()));
		observers.add(new VisibilityObserver(turtle, gc, width, height, turtleModel.getVisObs()));
		observers.add(new OutputObserver(turtle, gc, width, height, turtleModel.getOutObs(), display));
		return observers;
	}

	public void clearScreen() {
		myDisplay.getTurtleLand().reset();
		myDisplay.getTurtleLand().getTurtle().getPen().resetPath();
	}

	public Collection<Observer> getObservers() {
		return myObservers;
	}

	public void interpretInput(String input) {

		try {
			Interpreter.class.newInstance().interpretString(input);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
