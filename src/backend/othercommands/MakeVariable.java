package backend.othercommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;
import backend.VariableHouse;

public class MakeVariable extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;

	public MakeVariable(Turtle t, VariableHouse vh) {
		super("Variables", 2);
		myTurtle = t;
		myVariableHouse = vh;
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		
		myVariableHouse.makeVariable(inputs.get(0).compute(null), inputs.get(1).compute(null));
		
		return inputs.get(1).compute(null);
		
	}

}
