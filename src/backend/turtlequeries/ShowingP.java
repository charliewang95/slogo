package backend.turtlequeries;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class ShowingP extends Command {
	
	private Turtle myTurtle;
	private final String IS_VISABLE = "1";
	private final String IS_HIDING = "0";
	
	public ShowingP(Turtle t) {
		super("TurtleQuery", 0);
		myTurtle = t;
	}

	public String compute(ArrayList<Command> inputs) {
		
		if(! checkNoInputs(inputs) ) {
			return "0"; //might change because that is the expected output
		}
		
		if(myTurtle.isVisable()) {
			return IS_VISABLE;
		} else {
			return IS_HIDING;
		}
		
	}

}