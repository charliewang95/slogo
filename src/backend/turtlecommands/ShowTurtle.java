package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class ShowTurtle extends Command {
	
	private Turtle myTurtle;
	private final String ONE = "1";
	
	public ShowTurtle(Turtle t) {
		super("TurtleCommand", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		myTurtle.setVisible(true);
		
		return ONE;
		
	}

}
