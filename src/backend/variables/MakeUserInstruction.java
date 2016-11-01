package backend.variables;

import java.util.ArrayList;
import java.util.List;

import backend.Command;
import backend.CommandOperator;
import backend.Turtle;
import backend.VariableHouse;

public class MakeUserInstruction extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private final String ONE = "1";

	public MakeUserInstruction(Turtle t, VariableHouse vh) {
		super("Variables", -1);
		myTurtle = t;
		myVariableHouse = vh;
	}

	//assumes inputs is formatted correctly
	public String compute(ArrayList<Command> inputs) {
		
		String commandName = inputs.get(0).compute(null);
		List<String> commandVars = new ArrayList<String>();
		List<String> commandActions = new ArrayList<String>();
		
		int index = 2; //0 + 1 (for commandName) + 1 ( for [ ) = 2
		while(! ( inputs.get(index) instanceof CommandOperator ) ) {
			commandVars.add(inputs.get(index).compute(null));
			index++;
		}
		index+=2; //++ to get to the next command, which is ], and another to get pass that
		while(! ( inputs.get(index) instanceof CommandOperator ) ) {
			commandActions.add(inputs.get(index).compute(null));
			index++;
		}
		
		myVariableHouse.makeCommands(commandName, commandVars, commandActions);
		
		return ONE;
		
	}

}
