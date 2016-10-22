package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class HideTurtle extends Command {
	
	private Turtle myTurtle;
	private final String ZERO = "0";
	
	public HideTurtle(Turtle t) {
		super("TurtleCommand", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0"; //might change because that is the expected output
		}
		
		myTurtle.setVisable(false);
		
		return ZERO;
		
	}

}