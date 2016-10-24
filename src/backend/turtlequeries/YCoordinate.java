package backend.turtlequeries;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class YCoordinate extends Command {
	
	private Turtle myTurtle;
	
	public YCoordinate(Turtle t) {
		super("TurtleQuery", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
//		if(! checkNoInputs(inputs) ) {
//			return "0";
//		}
		System.out.println(myTurtle.getMyY());
		return ( (Double) myTurtle.getMyY() ).toString();
		
	}

}