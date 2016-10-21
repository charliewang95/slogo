package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class Right extends SpinMove {
	
	private Turtle myTurtle;
	
	Right(Turtle t) {
		super();
		myTurtle = t;
	}
		
	public String compute(ArrayList<Command> inputs) {
		
		return computeSpinMove(myTurtle, inputs, true); //true for clockwise direction
		
	}

}
