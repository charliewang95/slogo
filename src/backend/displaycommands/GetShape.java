package backend.displaycommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class GetShape extends Command {

	private Turtle myTurtle;

	public GetShape(Turtle t) {
		super("Display Command", 0);
		myTurtle = t;
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		
		return myTurtle.getShape();
		
	}

}
