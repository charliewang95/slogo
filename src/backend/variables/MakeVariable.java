package backend.variables;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class MakeVariable extends Command {
	
	private Turtle myTurtle;

	public MakeVariable(Turtle t, VariableHouse v) {
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
