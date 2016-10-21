package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class Left extends SpinMove {
	
	private Turtle myTurtle;
	
	Left(Turtle t) {
		super();
		myTurtle = t;
	}
		
	public String compute(ArrayList<Command> inputs) {
		
		return turn(myTurtle, inputs, false); //false for counter-clockwise direction
		
	}

}