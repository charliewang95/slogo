package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class Right extends SpinMove {
	
	private Turtle myTurtle;
	
	public Right(Turtle t) {
		super();
		myTurtle = t;
	}
		
	public String compute(ArrayList<Command> inputs) {
		
		return turn(myTurtle, inputs, true); //true for clockwise direction
		
	}

}