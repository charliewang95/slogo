package backend.othercommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;
/**
 * 
 * @author Tripp Whaley
 *
 */
public class MakeVariable extends Command {
	
	private Turtle myTurtle;

	public MakeVariable(Turtle t) {
		super("Variables", 2);
		myTurtle = t;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		// TODO Auto-generated method stub
		return null;
	}

}
