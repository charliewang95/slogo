package backend.turtlequeries;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class XCoordinate extends Command {
	
	private Turtle myTurtle;
	
	public XCoordinate(Turtle t) {
		super("TurtleQuery", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
//		if(! checkNoInputs(inputs) ) {
//			return "0";
//		}
		
		return ( (Double) myTurtle.getMyX() ).toString();
		
	}

}