package backend.turtlequeries;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class XCor extends Command {
	
	private Turtle myTurtle;
	
	public XCor(Turtle t) {
		super("TurtleCommand", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		return ( (Integer) myTurtle.getMyX() ).toString();
		
	}

}