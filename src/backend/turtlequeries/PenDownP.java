package backend.turtlequeries;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class PenDownP extends Command {
	
	private Turtle myTurtle;
	private final String PEN_IS_DOWN = "1";
	private final String PEN_IS_UP = "0";
	
	public PenDownP(Turtle t) {
		super("TurtleQuery", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0"; //might change because that is the expected output
		}
		
		if(myTurtle.isPenDown()) {
			return PEN_IS_DOWN;
		} else {
			return PEN_IS_UP;
		}
		
	}

}