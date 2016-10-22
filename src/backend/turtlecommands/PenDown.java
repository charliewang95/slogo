package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class PenDown extends Command {
	
	private Turtle myTurtle;
	private final String ONE = "1";
	
	public PenDown(Turtle t) {
		super("TurtleCommand", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		myTurtle.setPenDown(true);
		
		return ONE;
		
	}

}