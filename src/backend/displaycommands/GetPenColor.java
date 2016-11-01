package backend.displaycommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class GetPenColor extends Command {
	
	private Turtle myTurtle;

	public GetPenColor(Turtle t) {
		super("Display Command", 0);
		myTurtle = t;
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		
		return myTurtle.getPenColor();
		
	}

}
