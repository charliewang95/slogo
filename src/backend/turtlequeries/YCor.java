package backend.turtlequeries;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class YCor extends Command {
	
	private Turtle myTurtle;
	
	public YCor(Turtle t) {
		super("TurtleQuery", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		return ( (Double) myTurtle.getMyY() ).toString();
		
	}

}