package backend.othercommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;
import backend.VariableHouse;

/**
 * 
 * @author Alex Zaldastani
 *
 */
public class MakeUserInstruction extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;

	public MakeUserInstruction(Turtle t, VariableHouse vh) {
		super("Variables", 3);
		myTurtle = t;
		myVariableHouse = vh;
	}

	public String compute(ArrayList<Command> inputs) {
		
		//WIP
		
		return null;
		
	}

}
