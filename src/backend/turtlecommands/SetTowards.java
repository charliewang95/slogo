package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetTowards extends SpinMove {
	
	private Turtle myTurtle;
	
	public SetTowards(Turtle t) {
		super();
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		
		return toward(myTurtle, inputs);
		
	}	

}
