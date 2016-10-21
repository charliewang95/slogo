package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetHeading extends SpinMove{
	
	private Turtle myTurtle;
	
	public SetHeading(Turtle t) {
		super();
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		
		return set(myTurtle, inputs);
		
	}

}
