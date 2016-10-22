package backend.turtlequeries;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class Heading extends Command {
	
	private Turtle myTurtle;
	
	public Heading(Turtle t) {
		super("TurtleCommand", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		return ( (Integer) myTurtle.getDirection() ).toString();
		
	}

}